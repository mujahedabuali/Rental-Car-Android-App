<?php
require_once "../dbconfig.in.php";
if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $id = $_POST['id'];

    $sql = "DELETE FROM Car WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':id', $id);

    if ($stmt->execute()) {
        echo json_encode(["status" => "success", "message" => "Car deleted successfully."]);
    } else {
        echo json_encode(["status" => "error", "message" => "Car deletion failed."]);
    }
}
else {
    echo json_encode(["status" => "error", "message" => "Invalid request method or missing ID."]);
}
?>
