# Quick Reference Card

**Inventory Management System - Quick Commands & Information**

---

## 🚀 START HERE (3 Steps)

```bash
# Step 1: Navigate to backend
cd inventory-project/inventory-backend

# Step 2: Build application
C:\Users\DELL\.maven\maven-3.9.14\bin\mvn clean package -DskipTests

# Step 3: Run application
java -jar target/inventory-0.0.1-SNAPSHOT.jar
```

**Result:** Application opens on `http://localhost:8080/` ✅

---

## 📍 Key URLs

| Purpose | URL |
|---------|-----|
| **Frontend UI** | `http://localhost:8080/` |
| **API Base** | `http://localhost:8080/api/` |
| **Products API** | `http://localhost:8080/api/products` |
| **Categories API** | `http://localhost:8080/api/categories` |
| **H2 Console** | `http://localhost:8080/h2-console` |
| **WebSocket** | `ws://localhost:8080/ws` |

---

## 📁 Important Files & Folders

### Edit These (Development)
```
inventory-frontend/
├── js/app.js              ← Frontend logic
├── css/styles.css         ← UI styling
└── index.html             ← HTML markup

inventory-backend/src/main/java/com/example/inventory/
├── service/ProductService.java        ← Business logic
├── controller/ProductController.java  ← REST API
├── model/Product.java                 ← Entity
└── repository/ProductRepository.java  ← Database
```

### Don't Edit Directly (Auto-synced)
```
inventory-backend/src/main/resources/static/
├── js/app.js              ← Copied from inventory-frontend/js/app.js
├── css/styles.css         ← Copied from inventory-frontend/css/styles.css
└── index.html             ← Copied from inventory-frontend/index.html
```

---

## 🔄 Sync Frontend Files

### Option 1: Quick Manual Sync (During Dev)
```bash
cd inventory-project
sync-frontend.bat      # Windows
# or
bash sync-frontend.sh  # Linux/Mac
```

### Option 2: Full Rebuild (Includes Sync)
```bash
cd inventory-project/inventory-backend
mvn clean package -DskipTests
java -jar target/inventory-0.0.1-SNAPSHOT.jar
```

---

## 🔨 Common Maven Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Delete `target/` folder |
| `mvn compile` | Compile Java code |
| `mvn test` | Run unit tests |
| `mvn package` | Build JAR file |
| `mvn clean package -DskipTests` | **Fast build** (skip tests) |
| `mvn clean install` | Build + add to local repo |

---

## 🌐 REST API Quick Test

```powershell
# List all products
curl http://localhost:8080/api/products

# List all categories
curl http://localhost:8080/api/categories

# Create product
curl -X POST http://localhost:8080/api/products `
  -H "Content-Type: application/json" `
  -d '{"name":"Mouse","sku":"MOUSE-001","price":500,"quantity":10,"category":{"id":1}}'

# Delete product
curl -X DELETE http://localhost:8080/api/products/1
```

---

## 🐛 Troubleshooting Quick Fixes

| Problem | Quick Fix |
|---------|-----------|
| App won't start | Check port 8080 is free: `netstat -ano \| findstr :8080` |
| Frontend not updating | Run `sync-frontend.bat` then refresh browser |
| API returns 409 (Conflict) | SKU already exists; use unique SKU |
| Can't connect to localhost | Make sure `java -jar` process is running |
| Changes nothing after build | Wait 5 sec, hard refresh: `Ctrl+F5` |
| WebSocket not connecting | Restart backend; check browser console |

---

## 📊 Project Statistics

| Item | Value |
|------|-------|
| Frontend Files | 3 (HTML, JS, CSS) |
| Backend Controllers | 2 (Products, Categories) |
| Database | H2 (in-memory) |
| API Endpoints | 10+ |
| WebSocket Topics | 2 |
| Build Time | ~30 seconds |
| JAR Size | 47.3 MB |
| Java Version | 17+ |

---

## 📚 Documentation

| File | Purpose |
|------|---------|
| `README.md` | Project overview |
| `BUILD_AND_SYNC.md` | Build & sync instructions |
| `DEVELOPMENT_GUIDE.md` | Complete reference (⭐ Read this!) |
| `CHANGELOG.md` | All changes made |
| `QUICK_REFERENCE.md` | This file |

---

## 🎯 Typical Development Cycle

### For Frontend Changes
1. Edit file in `inventory-frontend/js/` or `css/`
2. Run `sync-frontend.bat`
3. Restart backend (if running) or rebuild
4. Refresh browser (`Ctrl+F5`)

### For Backend Changes
1. Edit file in `inventory-backend/src/main/java/`
2. Run `mvn clean package -DskipTests`
3. Run `java -jar target/inventory-0.0.1-SNAPSHOT.jar`
4. Refresh browser

### For Database Changes
1. Edit entity in `model/` folder
2. Hibernate auto-creates/updates schema (ddl-auto=update)
3. Rebuild and restart

---

## 💡 Pro Tips

✅ **Always** sync frontend before rebuilding  
✅ **Always** test API with curl before debugging UI  
✅ **Always** check browser console (F12) for JavaScript errors  
✅ **Always** check terminal output for backend errors  

❌ **Don't** edit `inventory-backend/src/main/resources/static/` directly  
❌ **Don't** hardcode URLs (use relative paths)  
❌ **Don't** commit JAR files (too large)  
❌ **Don't** forget to sync after frontend edits  

---

## 🔗 API Response Examples

### GET /api/products (200 OK)
```json
[
  {
    "id": 1,
    "name": "USB Cable",
    "sku": "USB-001",
    "price": 199.0,
    "quantity": 50,
    "category": { "id": 1, "name": "Electronics" }
  }
]
```

### POST /api/products (201 Created)
```bash
Request:
{
  "name": "Laptop",
  "sku": "LAP-001",
  "price": 89999,
  "quantity": 5,
  "category": { "id": 1 }
}

Response:
{
  "id": 3,
  "name": "Laptop",
  "sku": "LAP-001",
  "price": 89999.0,
  "quantity": 5,
  "category": { "id": 1, "name": "Electronics" }
}
```

### DELETE /api/products/1 (204 No Content)
```
(empty response)
```

---

## 📞 Need Help?

1. **For frontend issues** → Check `inventory-frontend/js/app.js`
2. **For API issues** → Check `ProductController.java` or curl output
3. **For sync issues** → Check `BUILD_AND_SYNC.md`
4. **For general info** → Check `DEVELOPMENT_GUIDE.md`
5. **For what changed** → Check `CHANGELOG.md`

---

**Last Updated:** April 8, 2026  
**Status:** ✅ Production Ready  
**Version:** 1.0
