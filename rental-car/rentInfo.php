<?php
require_once 'dbconfig.in.php';

$carID = $_GET['carID']; 

try {
    $stmt = $pdo->prepare("
        SELECT 
            car.ID AS carID,
            rent.ID AS rentID,
            rent.TotalPrice,
            rent.status,
            rent.startDate,
            rent.endDate,
            account.ID AS accountID,
            account.name,
            account.Phone,
            account.email
        FROM car
        INNER JOIN rent ON car.ID = rent.carID
        INNER JOIN account ON rent.AccountID = account.ID
        WHERE car.ID = :carID AND rent.status = 'now'
    ");

    $stmt->execute(['carID' => $carID]);

    $result = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($result) {
        $startDate = new DateTime($result['startDate']);
        $endDate = new DateTime($result['endDate']);
        $interval = $startDate->diff($endDate);
        $totalDays = $interval->days;

        $result['totalDays'] = $totalDays;

        echo json_encode($result);
    } else {
        echo json_encode(['message' => 'No rentals found for this car with status "now".']);
    }
} catch (PDOException $e) {
    echo 'Connection failed: ' . $e->getMessage();
}
?>
