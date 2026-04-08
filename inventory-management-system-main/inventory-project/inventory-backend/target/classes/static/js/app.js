const API_BASE = "/api";
let stompClient = null;

async function fetchCategories() {
  const res = await fetch(`${API_BASE}/categories`);
  if (!res.ok) throw new Error(`Failed to load categories (${res.status})`);
  return res.json();
}

async function fetchProducts() {
  const res = await fetch(`${API_BASE}/products`);
  if (!res.ok) throw new Error(`Failed to load products (${res.status})`);
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
  try {
    const products = await fetchProducts();
    renderProducts(products);
  } catch (err) {
    console.error(err);
    alert("Unable to load products. Please refresh the page.");
  }
}

async function populateCategories() {
  try {
    const categories = await fetchCategories();
    const sel = document.getElementById("categorySelect");
    sel.innerHTML = `<option value="">-- Select Category --</option>`;
    categories.forEach(c => {
      const opt = document.createElement("option");
      opt.value = c.id;
      opt.textContent = c.name;
      sel.appendChild(opt);
    });
  } catch (err) {
    console.error(err);
    alert("Unable to load categories. Product creation may fail.");
  }
}

// form submit
document.getElementById("productForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const data = {
    name: form.name.value.trim(),
    sku: form.sku.value.trim(),
    price: Number(form.price.value) || 0,
    quantity: Number(form.quantity.value) || 0,
    category: form.categoryId.value ? { id: Number(form.categoryId.value) } : null
  };

  if (!data.name || !data.sku) {
    alert("Name and SKU are required.");
    return;
  }

  try {
    const res = await fetch(`${API_BASE}/products`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (res.ok) {
      form.reset();
      await loadProducts();
    } else {
      const errorText = await res.text();
      alert("Failed to add product: " + (errorText || res.statusText));
    }
  } catch (err) {
    console.error(err);
    alert("Network error while adding product. Check your backend connection.");
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
  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.debug = null;

  stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/products', function(message) {
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
