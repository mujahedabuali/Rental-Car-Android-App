<?php
require_once "../dbconfig.in.php";
if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $company = $_POST["company"];
    $seats_number = $_POST["SeatsNumber"];
    $model_year = $_POST["Model_year"];
    $MonthlyPrice = $_POST["MonthlyPrice"];
    $Mileage = $_POST["Mileage"];
    $DailyPrice = $_POST["DailyPrice"];
    $color = $_POST["color"];
    $image = $_POST["image"];

    $id = $_POST["id"];

    $sql = "UPDATE Car SET company = :company, Model_year = :Model_year, SeatsNumber = :SeatsNumber, 
    MonthlyPrice = :MonthlyPrice, DailyPrice = :DailyPrice, color = :color ,image= :image
    WHERE id = :id";
    $stmt = $pdo->prepare($sql);

    $stmt->bindValue(':company', $company);
    $stmt->bindValue(':Model_year', $model_year);
    $stmt->bindValue(':SeatsNumber', $seats_number);
    $stmt->bindValue(':MonthlyPrice', $MonthlyPrice);
    $stmt->bindValue(':DailyPrice', $DailyPrice);
    $stmt->bindValue(':color', $color);
    $stmt->bindValue(':image', $image);
    $stmt->bindValue(':id', $id);

    if ($stmt->execute()) {
        echo json_encode(["status" => "success", "message" => "Car updated successfully."]);
    } else {
        echo json_encode(["status" => "error", "message" => "Car update failed."]);
    }
}
else {
    echo json_encode(["status" => "error", "message" => "Invalid request method or missing ID."]);
}
?>
