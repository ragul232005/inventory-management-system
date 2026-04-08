# Build & Sync Guide

This guide explains how frontend files are automatically synchronized with the backend during the build process.

---

## Automatic Sync During Maven Build

### How It Works
When you build the backend with Maven (`mvn clean package`), the build automatically:
1. Copies all frontend files from `inventory-frontend/`
2. Places them in `inventory-backend/src/main/resources/static/`
3. Includes them in the final JAR file

### Build Process
```bash
# From inventory-backend directory
mvn clean package -DskipTests
```

**What happens:**
- Phase 1: `generate-resources` → Copies frontend files
- Phase 2: Compiles Java code
- Phase 3: Packages everything into `target/inventory-0.0.1-SNAPSHOT.jar`

### Result
The JAR file contains:
- ✓ Backend API code
- ✓ Latest frontend UI (automatically copied)
- ✓ WebSocket configuration
- ✓ Database configuration

---

## Manual Sync (During Development)

If you modify frontend files and want to sync **without rebuilding**, use one of these scripts:

### Windows
```bash
cd inventory-project
sync-frontend.bat
```

### Linux/Mac
```bash
cd inventory-project
bash sync-frontend.sh
```

These scripts copy files manually in seconds without a full Maven rebuild.

---

## Workflow Recommendations

### For Frontend-Only Changes
1. Edit file in `inventory-frontend/` (e.g., `js/app.js`)
2. Run `sync-frontend.bat` (or `.sh` on Linux/Mac)
3. Restart the running backend app to pick up changes
4. Refresh browser to see updates

### For Backend Changes
1. Edit file in `inventory-backend/src/main/java/`
2. Run `mvn clean package -DskipTests`
3. Start the new JAR: `java -jar target/inventory-0.0.1-SNAPSHOT.jar`
4. Refresh browser

### For Full Rebuilds
```bash
# From inventory-backend
mvn clean package -DskipTests
java -jar target/inventory-0.0.1-SNAPSHOT.jar
```

---

## File Structure Recap

```
inventory-project/
├── sync-frontend.bat            ← Run to manually sync (Windows)
├── sync-frontend.sh             ← Run to manually sync (Linux/Mac)
│
├── inventory-frontend/          ← Source (edit here)
│   ├── index.html
│   ├── js/app.js
│   └── css/styles.css
│
└── inventory-backend/           
    ├── pom.xml                  ← Auto-copy configured here
    └── src/main/resources/static/ ← Synced destination
        ├── index.html
        ├── js/app.js
        └── css/styles.css
```

---

## Troubleshooting

### Files not syncing?
1. Check path: Make sure you're in the `inventory-project/` directory
2. Check folders exist: Both `inventory-frontend/` and backend static should exist
3. Try manual sync: Run `sync-frontend.bat` or `sync-frontend.sh`
4. Force rebuild: `mvn clean package -DskipTests`

### Frontend changes not appearing?
1. Sync files: `sync-frontend.bat` or rebuild with Maven
2. Restart backend: The app needs to reload static files
3. Clear browser cache: Ctrl+Shift+Del (Windows/Linux) or Cmd+Shift+Del (Mac)
4. Hard refresh: Ctrl+F5 or Cmd+Shift+R

---

## Summary
- **Automatic**: Maven automatically syncs during `mvn package`
- **Manual**: Use `sync-frontend.bat` for quick updates
- **Reliable**: Both methods ensure frontend and backend stay in sync
- **Single Source**: Always edit in `inventory-frontend/`, never directly in backend static
