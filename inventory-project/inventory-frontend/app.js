const API_BASE = "http://localhost:8080/api";
let stompClient = null;

async function fetchCategories() {
  const res = await fetch(`${API_BASE}/categories`);
  return res.json();
}

async function fetchProducts() {
  const res = await fetch(`${API_BASE}/products`);
  return res.json();
}

function renderProducts(products) {
  const tbody = document.querySelector("#productsTable tbody");
  tbody.innerHTML = "";
  products.forEach(p => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${p.id}</td>
      <td>${p.name}</td>
      <td>${p.sku}</td>
      <td>${p.category ? p.category.name : ""}</td>
      <td>${p.price ?? ""}</td>
      <td>${p.quantity ?? 0}</td>
      <td>
        <button data-id="${p.id}" class="deleteBtn">Delete</button>
      </td>
    `;
    tbody.appendChild(tr);
  });
  setDeleteHandlers();
}

function setDeleteHandlers() {
  document.querySelectorAll(".deleteBtn").forEach(btn => {
    btn.onclick = async () => {
      const id = btn.dataset.id;
      await fetch(`${API_BASE}/products/${id}`, { method: "DELETE" });
      await loadProducts();
    };
  });
}

async function loadProducts() {
  const products = await fetchProducts();
  renderProducts(products);
}

async function populateCategories() {
  const categories = await fetchCategories();
  const sel = document.getElementById("categorySelect");
  sel.innerHTML = `<option value="">-- Select Category --</option>`;
  categories.forEach(c => {
    const opt = document.createElement("option");
    opt.value = c.id;
    opt.textContent = c.name;
    sel.appendChild(opt);
  });
}

// form submit
document.getElementById("productForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const data = {
    name: form.name.value,
    sku: form.sku.value,
    price: parseFloat(form.price.value || 0),
    quantity: parseInt(form.quantity.value || 0),
    category: form.categoryId.value ? { id: parseInt(form.categoryId.value) } : null
  };
  const res = await fetch(`${API_BASE}/products`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  if (res.ok) {
    form.reset();
    // server will broadcast update -> client will refresh via websocket message
  } else {
    alert("Failed to add product");
  }
});

// refresh button
document.getElementById("refreshBtn").addEventListener("click", loadProducts);

// search
document.getElementById("search").addEventListener("input", async (e) => {
  const q = e.target.value.toLowerCase();
  const products = await fetchProducts();
  const filtered = products.filter(p => 
    (p.name && p.name.toLowerCase().includes(q)) ||
    (p.sku && p.sku.toLowerCase().includes(q))
  );
  renderProducts(filtered);
});

// WebSocket connection & handlers
function connectWebSocket() {
  const socket = new SockJS('http://localhost:8080/ws');
  stompClient = Stomp.over(socket);
  // disable debug logs in console for clarity
  stompClient.debug = null;

  stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    // subscribe to product updates
    stompClient.subscribe('/topic/products', function(message) {
      // server sends the updated product object
      // simply re-fetch the product list to keep UI simple
      loadProducts();
    });

    stompClient.subscribe('/topic/products/delete', function(message) {
      loadProducts();
    });
  }, function(err) {
    console.error('WebSocket error', err);
  });
}

// initialize
(async function init() {
  await populateCategories();
  await loadProducts();
  connectWebSocket();
})();
