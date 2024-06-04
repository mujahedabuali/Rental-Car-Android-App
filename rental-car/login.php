<?php

require_once 'dbconfig.in.php';

if ($_SERVER['REQUEST_METHOD'] === "GET") {
    try {
        $sql = "SELECT * FROM account";
        $stmt = $pdo->query($sql);
        $accounts = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode(["status" => "success", "data" => $accounts]);
    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
    exit();
}

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
        $sql = "SELECT id, type, password FROM account WHERE email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':email', $email);
        $stmt->execute();
        $account = $stmt->fetch();

        if ($account && password_verify($password, $account['password'])) {
            echo json_encode(["status" => "success", "message" => "Login successful.", "account_id" => $account['id'], "account_type" => $account["type"]]);
        } else {
            echo json_encode(["status" => "error", "message" => "Invalid email or password."]);
        }
    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
}
?>
