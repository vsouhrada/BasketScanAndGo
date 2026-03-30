#!/bin/bash

# Get the script directory and project root
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# Get the local IP address (works on macOS)
LOCAL_IP=$(ipconfig getifaddr en0 2>/dev/null || ipconfig getifaddr en1 2>/dev/null)

if [ -z "$LOCAL_IP" ]; then
    echo "Could not detect local IP address"
    exit 1
fi

echo "Detected IP: $LOCAL_IP"

# Update the client-side server path provider
CLIENT_FILE="$PROJECT_ROOT/core/logic/data/src/commonMain/kotlin/com/basket/sample/scango/data/common/api/providers/BasketApiServerPathProviderImpl.kt"

if [ -f "$CLIENT_FILE" ]; then
    # Replace the IP address in the file
    sed -i '' "s|http://[0-9][0-9]*\.[0-9][0-9]*\.[0-9][0-9]*\.[0-9][0-9]*:8080|http://${LOCAL_IP}:8080|g" "$CLIENT_FILE"
    echo "Updated $CLIENT_FILE with IP: $LOCAL_IP"
else
    echo "File not found: $CLIENT_FILE"
    exit 1
fi

echo "Done! Server IP updated to: $LOCAL_IP"
echo "Remember: Server is configured to listen on 0.0.0.0 (all interfaces)"
