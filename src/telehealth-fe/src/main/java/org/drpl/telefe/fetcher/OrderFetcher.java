package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.drpl.telefe.Global;
import org.drpl.telefe.dto.OrderRequest;
import org.drpl.telefe.dto.OrderResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class OrderFetcher {

    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OrderFetcher() {
        this.baseUrl = Global.BASE_URL + "/orders";
        this.httpClient = HttpClient.newBuilder().build();
        this.objectMapper = Global.getObjectMapper();
        System.out.println(baseUrl);
    }

    public String createOrder(OrderRequest request) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(request);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to create order: " + response.body() + " Status: " + response.statusCode());
        }
        return response.body();
    }

    public List<OrderResponse> getAllOrders() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch all orders: " + response.body() + " Status: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, OrderResponse.class));
    }

    public Optional<OrderResponse> getOrderById(Long orderId) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/" + orderId))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return Optional.empty();
        } else if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch order by ID " + orderId + ": " + response.body() + " Status: " + response.statusCode());
        }
        return Optional.of(objectMapper.readValue(response.body(), OrderResponse.class));
    }

    public String updateOrder(Long orderId, OrderRequest request) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(request);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/" + orderId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to update order " + orderId + ": " + response.body() + " Status: " + response.statusCode());
        }
        return response.body();
    }

    public String finishOrder(Long orderId) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/" + orderId + "/finish"))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to finish order " + orderId + ": " + response.body() + " Status: " + response.statusCode());
        }
        return response.body();
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/by-user/" + userId))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch orders by user ID " + userId + ": " + response.body() + " Status: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, OrderResponse.class));
    }

    public static void main(String[] args) {
        OrderFetcher orderFetcher = new OrderFetcher();

        try {
            Long patientId = 1L;       // Replace with a valid patient ID
            Long pharmacistId = 2L;  // Replace with a valid pharmacist ID

            // 1. Create a new Order
            System.out.println("=== 1. Creating Order ===");
            OrderRequest newOrder = new OrderRequest(patientId, pharmacistId, false);
            String createdResponse = orderFetcher.createOrder(newOrder);
            System.out.println("Create Order Response: " + createdResponse);
            System.out.println();

            // 2. Get all orders
            System.out.println("=== 2. Fetching All Orders ===");
            List<OrderResponse> allOrders = orderFetcher.getAllOrders();
            allOrders.forEach(order -> System.out.println("  ID: " + order.getId() + ", Paid: " + order.isPaid() + ", Price: " + order.getTotalPrice()));
            System.out.println();

            // Pick the last order (assumes newest is last)
            OrderResponse testOrder = allOrders.get(allOrders.size() - 1);
            Long testOrderId = testOrder.getId();

            // 3. Get order by ID
            System.out.println("=== 3. Fetching Order by ID: " + testOrderId + " ===");
            Optional<OrderResponse> fetchedOrder = orderFetcher.getOrderById(testOrderId);
            fetchedOrder.ifPresentOrElse(
                    order -> System.out.println("Fetched: ID=" + order.getId() + ", PatientID=" + order.getPatientId() + ", Paid=" + order.isPaid()),
                    () -> System.out.println("Order not found.")
            );
            System.out.println();

            // 4. Update the order to paid
            System.out.println("=== 4. Updating Order ID " + testOrderId + " to PAID ===");
            OrderRequest updatedOrder = new OrderRequest(patientId, pharmacistId, true);
            String updateResponse = orderFetcher.updateOrder(testOrderId, updatedOrder);
            System.out.println("Update Response: " + updateResponse);
            System.out.println();

            // 5. Finish the order
            System.out.println("=== 5. Finishing Order ID " + testOrderId + " ===");
            String finishResponse = orderFetcher.finishOrder(testOrderId);
            System.out.println("Finish Response: " + finishResponse);
            System.out.println();

            // 6. Fetch orders by pharmacist ID
            System.out.println("=== 6. Fetching Orders by Pharmacist/User ID: " + pharmacistId + " ===");
            List<OrderResponse> ordersByUser = orderFetcher.getOrdersByUserId(pharmacistId);
            ordersByUser.forEach(order -> System.out.println("  ID: " + order.getId() + ", Paid: " + order.isPaid()));
            System.out.println();

        } catch (IOException | InterruptedException e) {
            System.err.println("IO/Network Error: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("API Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}