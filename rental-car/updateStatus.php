<?php
require_once 'dbconfig.in.php';

$currentDate = date('Y-m-d');

try {
 
    $sql = "SELECT carID FROM rent WHERE endDate <= :currentDate AND status != 'Free'";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':currentDate', $currentDate);
    $stmt->execute();

    if ($stmt->rowCount() > 0) {
        // Loop through each rental
        while ($row = $stmt->fetch()) {
            $carID = $row['carID'];
            $updateSql = "UPDATE car SET status = 'Free' WHERE ID = :carID";
            $updateStmt = $pdo->prepare($updateSql);
            $updateStmt->bindParam(':carID', $carID);
            $updateStmt->execute();
        }
    } else {
    }
} catch (PDOException $e) {
}

?>
