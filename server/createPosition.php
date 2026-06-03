<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include_once 'service/PositionService.php';

    $latitude = $_POST['latitude'] ?? null;
    $longitude = $_POST['longitude'] ?? null;
    $datePosition = $_POST['date_position'] ?? null;
    $imei = $_POST['imei'] ?? 'unknown';

    if ($latitude && $longitude && $datePosition) {
        $service = new PositionService();
        $position = new Position(null, $latitude, $longitude, $datePosition, $imei);
        $service->create($position);

        echo "Position enregistrée avec succès : Lat $latitude, Lon $longitude";
    } else {
        echo "Erreur : Paramètres manquants";
    }
} else {
    echo "Méthode non autorisée";
}
?>