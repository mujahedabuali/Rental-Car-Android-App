<?php
require_once "../dbconfig.in.php";
if ($_SERVER['REQUEST_METHOD'] === "POST") {

    $company = $_POST["company"];
    $seats_number = $_POST["SeatsNumber"];
    $model_year = $_POST["model_year"];
    $MonthlyPrice = $_POST["MonthlyPrice"];
    $Mileage = $_POST["Mileage"];
    $DailyPrice = $_POST["DailyPrice"];
    $color = $_POST["color"];
    $image = $_POST["image"];
    $sql = "INSERT INTO Car (company, Model_year, SeatsNumber, MonthlyPrice, DailyPrice,color,image,Mileage) VALUES (:company, :Model_year, :SeatsNumber, :MonthlyPrice, :DailyPrice , :color , :image,:Mileage )";
    $stmt = $pdo->prepare($sql);
    
    $stmt->bindValue(':company', $company);
    $stmt->bindValue(':Model_year', $model_year);
    $stmt->bindValue(':SeatsNumber', $seats_number);
    $stmt->bindValue(':MonthlyPrice', $MonthlyPrice);
    $stmt->bindValue(':DailyPrice', $DailyPrice);
    $stmt->bindValue(':color', $color);
    $stmt->bindValue(':image', $image);
    $stmt->bindValue(':Mileage', $Mileage);

    if ($stmt->execute()) {
        echo json_encode(["status" => "success", "message" => "car  add successfully."]);
    } else {
        echo json_encode(["status" => "error", "message" => "car add failed."]);
    }
}
elseif ($_SERVER['REQUEST_METHOD'] === "GET" && isset($_GET['id'])) {
    $id = $_GET['id'];
    $sql = "SELECT * FROM Car WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':id', $id);
    $stmt->execute();
    $car = $stmt->fetch();

    if ($car) {
        echo json_encode(["status" => "success", "data" => $car]);
    } else {
        echo json_encode(["status" => "error", "message" => "No data found for the given ID."]);
    }
}
else {
    echo json_encode(["status" => "error", "message" => "Invalid request method or missing ID."]);
}
?>
