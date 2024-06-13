<?php
require_once "../dbconfig.in.php";

if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $id = $_POST['id'];

    $checkSql = "SELECT status FROM Car WHERE id = :id";
    $checkStmt = $pdo->prepare($checkSql);
    $checkStmt->bindValue(':id', $id);
    $checkStmt->execute();

    $car = $checkStmt->fetch();

    if ($car) {
        if ($car['status'] === 'Free') {
            $deleteSql = "DELETE FROM Car WHERE id = :id";
            $deleteStmt = $pdo->prepare($deleteSql);
            $deleteStmt->bindValue(':id', $id);

            if ($deleteStmt->execute()) {
                echo json_encode(["status" => "success", "message" => "Car deleted successfully."]);
            } else {
                echo json_encode(["status" => "error", "message" => "Car deletion failed."]);
            }
        } else {
            echo json_encode(["status" => "error", "message" => "The car is rented Now !!! 
 You Cant delete it ... "]);
        }
    } else {
        echo json_encode(["status" => "error", "message" => "Car not found."]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid request method or missing ID."]);
}
?>
