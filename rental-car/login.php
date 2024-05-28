<?php

require_once 'dbconfig.in.php';

if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $email = $_POST["email"];
    $password = $_POST["password"];

    $errors = [];
    if (empty($email) || !filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $errors[] = "Invalid email.";
    }
    if (empty($password)) {
        $errors[] = "Password is required.";
    }

    if (count($errors) > 0) {
        echo json_encode(["status" => "error", "messages" => $errors]);
        exit();
    }


    try {
        $sql = "SELECT id,type ,  password FROM Account WHERE email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':email', $email);
        $stmt->execute();
        $Account = $stmt->fetch();
        if ($Account && password_verify($password, $Account['password'])) {
            echo json_encode(["status" => "success", "message" => "Login successful.", "Account_id" => $Account['id'], "Account_type" => $Account["type"]] );
        } else {
            echo json_encode(["status" => "error", "message" => "Invalid email or password."]);
        }


    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
}
?>