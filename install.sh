#!/bin/bash

# Gucken ob Docker und Docker-Compose installiert sind
if ! command -v docker &> /dev/null
then
    echo "❌ Die KletterApp läuft in Docker. Bitte installiere Docker und Docker-Compose um fortzufahren."
    exit 1
else
    echo "✳️ Docker ist installiert."
fi

if ! command -v docker compose &> /dev/null
then
    echo "❌ Wir brauchen auch Docker-Compose. Bitte installiere Docker-Compose um fortzufahren."
    exit 1
else
    echo "✳️ Docker-Compose ist installiert."
fi

# Volumes erstellen
docker volume create kletterapp-data
docker volume create kletterapp-uploads
echo "✳️ Docker-Volumes wurden erstellt."

echo "😊 Alle Voraussetzungen wurden erfüllt!"
echo ""

read -p "Auf welcher Domain soll die KletterApp erreichbar sein? " DOMAIN
echo "✳️ Domain wurde gesetzt: $DOMAIN"
echo ""


# Environment-Variablen setzen
read -p "Wähle einen Nutzernamen für die Datenbank: " DB_USER

while true; do
    read -s -p "❔ Wähle ein Passwort für die Datenbank: " DB_PASSWORD
    echo ""
    read -s -p "❔ Gib das Passwort erneut ein: " DB_PASSWORD_CONFIRM
    echo ""
    if [ "$DB_PASSWORD" != "$DB_PASSWORD_CONFIRM" ]; then
        echo "❌ Die Passwörter stimmen nicht überein. Bitte versuche es erneut."
    else
        break
    fi
done
echo "✳️ Datenbank-Zugangsdaten wurden gesetzt."

read -p "Wähle einen Nutzernamen für die Keycloak-Datenbank: " KC_USER
while true; do
    read -s -p "❔ Wähle ein Passwort für Keycloak: " KC_PASSWORD
    echo ""
    read -s -p "❔ Gib das Passwort erneut ein: " KC_PASSWORD_CONFIRM
    echo ""
    if [ "$KC_PASSWORD" != "$KC_PASSWORD_CONFIRM" ]; then
        echo "❌ Die Passwörter stimmen nicht überein. Bitte versuche es erneut."
    else
        break
    fi
done
echo "✳️ Keycloak-Zugangsdaten wurden gesetzt."

echo ""

read -p "Wähle einen Admin-Nutzernamen für die KletterApp: " ADMIN_USER

while true; do
    read -s -p "❔ Wähle ein Passwort für den Admin-Nutzer: " ADMIN_PASSWORD
    echo ""
    read -s -p "❔ Gib das Passwort erneut ein: " ADMIN_PASSWORD_CONFIRM
    echo ""
    if [ "$ADMIN_PASSWORD" != "$ADMIN_PASSWORD_CONFIRM" ]; then
        echo "❌ Die Passwörter stimmen nicht überein. Bitte versuche es erneut."
    else
        break
    fi
done
echo "✳️ Admin-Zugangsdaten wurden gesetzt."
echo "😊 Alle Zugangsdaten wurden gesetzt!"
echo ""

# .env Datei erstellen
cat << EOF > .env 
DOMAIN=$DOMAIN
POSTGRES_USER=$DB_USER
POSTGRES_PASSWORD=$DB_PASSWORD
DB_VOLUME_NAME=kletterapp-data
UPLOADS_VOLUME_NAME=kletterapp-uploads
KC_HTTP_RELATIVE_PATH=/auth
KC_DB=postgres
KC_DB_URL_HOST=database
KC_DB_URL_DATABASE=keycloak-db
KC_DB_USERNAME=$KC_USER
KC_DB_PASSWORD=$KC_PASSWORD
KC_DB_SCHEMA=public
KC_BOOTSTRAP_ADMIN_USERNAME=$ADMIN_USER
KC_BOOTSTRAP_ADMIN_PASSWORD=$ADMIN_PASSWORD
EOF

chmod 600 .env

echo "✳️ .env-Datei wurde erstellt. Du kannst sie jederzeit anpassen."
echo "Los gehts!"

docker compose up -d

echo "Die KletterApp ist jetzt gestartet und unter http://$DOMAIN erreichbar. Viel Spaß beim Klettern! 🧗‍♂️"