#!/bin/bash
# Bash script to copy frontend files to backend static resources
# Usage: Run this from the inventory-project folder

FRONTEND_DIR="inventory-frontend"
BACKEND_STATIC="inventory-backend/src/main/resources/static"

echo ""
echo "========================================"
echo "Frontend to Backend Static Sync Script"
echo "========================================"
echo ""

if [ ! -d "$FRONTEND_DIR" ]; then
    echo "ERROR: Frontend folder not found at: $FRONTEND_DIR"
    exit 1
fi

if [ ! -d "$BACKEND_STATIC" ]; then
    echo "ERROR: Backend static folder not found at: $BACKEND_STATIC"
    exit 1
fi

echo "Syncing files..."
echo ""

# Copy index.html
echo "Copying index.html..."
cp "$FRONTEND_DIR/index.html" "$BACKEND_STATIC/index.html"

# Copy js folder
echo "Copying js folder..."
mkdir -p "$BACKEND_STATIC/js"
cp -r "$FRONTEND_DIR/js/"* "$BACKEND_STATIC/js/"

# Copy css folder
echo "Copying css folder..."
mkdir -p "$BACKEND_STATIC/css"
cp -r "$FRONTEND_DIR/css/"* "$BACKEND_STATIC/css/"

# Copy assets folder if it exists
if [ -d "$FRONTEND_DIR/assets" ]; then
    echo "Copying assets folder..."
    mkdir -p "$BACKEND_STATIC/assets"
    cp -r "$FRONTEND_DIR/assets/"* "$BACKEND_STATIC/assets/" 2>/dev/null || true
fi

echo ""
echo "========================================"
echo "✓ Sync complete!"
echo "========================================"
echo ""
echo "Frontend source: $FRONTEND_DIR"
echo "Backend target: $BACKEND_STATIC"
echo ""
