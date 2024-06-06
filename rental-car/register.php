<?php
require_once 'dbconfig.in.php';

if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $email = $_POST["email"];
    $name = $_POST["name"];
    $password = $_POST["password"];
    $gender = $_POST["gender"];
    $phone = $_POST["phone"];
    $type = $_POST["type"];
    
    $errors = [];
    
    try {
        $checkEmailSql = "SELECT COUNT(*) FROM account WHERE email = :email";
        $checkEmailStmt = $pdo->prepare($checkEmailSql);
        $checkEmailStmt->bindValue(':email', $email);
        $checkEmailStmt->execute();
        $emailExists = $checkEmailStmt->fetchColumn();

        if ($emailExists > 0) {
            $errors[] = "Email is already used.";
        }

        if (count($errors) > 0) {
            echo json_encode(["status" => "error", "messages" => $errors]);
            exit();
        }
        $sql = "INSERT INTO account (name, Type, Phone, password, email,gender) VALUES (:name, :type, :phone, :pass, :email,:gender)";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindValue(':name', $name);
        $stmt->bindValue(':type', $type);
        $stmt->bindValue(':phone', $phone);
        $stmt->bindValue(':pass', $password);
        $stmt->bindValue(':email', $email);
        $stmt->bindValue(':gender', $gender);
        

        if ($stmt->execute()) {
            echo json_encode(["status" => "success", "message" => "User registered successfully."]);
        } else {
            echo json_encode(["status" => "error", "message" => "User registration failed."]);
        }
    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
}
?>
