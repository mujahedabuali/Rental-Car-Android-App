<?php
require_once 'dbconfig.in.php';
require_once 'updateStatus.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $stmt = $pdo->query('SELECT * FROM car WHERE status = "Free"');
    $cars = $stmt->fetchAll();
    header('Content-Type: application/json');
    echo json_encode($cars);
} else {
    header('HTTP/1.1 405 Method Not Allowed');
    echo json_encode(['error' => 'Only GET method is allowed']);
}


?>
