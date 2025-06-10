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
            // 1. Create an Order
            // if it fails (code 500), its because there cant be duplicates
            System.out.println("--- Creating an Order ---");
            OrderRequest newOrder = new OrderRequest(1L, 2L, 1L, false); // Replace with actual existing IDs
            String createMessage = orderFetcher.createOrder(newOrder);
            System.out.println("Create Order Response: " + createMessage);
            System.out.println();

            // 2. Get All Orders
            System.out.println("--- Getting All Orders ---");
            List<OrderResponse> allOrders = orderFetcher.getAllOrders();
            System.out.println("All Orders:");
            allOrders.forEach(order -> System.out.println("  ID: " + order.getId() + ", Paid: " + order.isPaid() + ", Total: " + order.getTotalPrice()));
            System.out.println();

            // 3. Get Order by ID (assuming order with ID 1 exists after creation or from pre-existing data)
            Long orderIdToFetch = 1L; // Use an ID that exists in your database
            System.out.println("--- Getting Order by ID: " + orderIdToFetch + " ---");
            Optional<OrderResponse> fetchedOrder = orderFetcher.getOrderById(orderIdToFetch);
            fetchedOrder.ifPresentOrElse(
                    order -> System.out.println("Fetched Order: ID=" + order.getId() + ", Patient ID=" + order.getPatientId() + ", Paid=" + order.isPaid()),
                    () -> System.out.println("Order with ID " + orderIdToFetch + " not found.")
            );
            System.out.println();

            // 4. Update an Order (assuming order with ID 1 exists)
            System.out.println("--- Updating Order with ID: " + orderIdToFetch + " ---");
            OrderRequest updatedOrder = new OrderRequest(1L, 2L, 1L, true); // Set to paid
            String updateMessage = orderFetcher.updateOrder(orderIdToFetch, updatedOrder);
            System.out.println("Update Order Response: " + updateMessage);
            System.out.println();

            // 5. Finish an Order (assuming order with ID 1 exists and is not yet finished)
            System.out.println("--- Finishing Order with ID: " + orderIdToFetch + " ---");
            String finishMessage = orderFetcher.finishOrder(orderIdToFetch);
            System.out.println("Finish Order Response: " + finishMessage);
            System.out.println();

            // 6. Get Orders by User ID (assuming user 101 exists)
            Long userIdToFetch = 101L; // Replace with an actual user ID
            System.out.println("--- Getting Orders by User ID: " + userIdToFetch + " ---");
            List<OrderResponse> userOrders = orderFetcher.getOrdersByUserId(userIdToFetch);
            System.out.println("Orders for User " + userIdToFetch + ":");
            userOrders.forEach(order -> System.out.println("  ID: " + order.getId() + ", Paid: " + order.isPaid()));
            System.out.println();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error during API call: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Backend Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}