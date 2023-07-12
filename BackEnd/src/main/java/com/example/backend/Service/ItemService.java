package com.example.backend.Service;

import com.example.backend.Repo.ItemRepository;
import com.example.backend.Repo.OrderRepository;
import com.example.backend.model.Item;
import com.example.backend.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    private final EmailService  emailService;

    @Autowired
    public ItemService(ItemRepository itemRepository,  OrderRepository orderRepository, EmailService emailService) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.emailService = emailService;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(Item item) {
            return itemRepository.save(item);
    }
    public List<Item> filterItems(String searchTerm) {
        List<Item> allItems = itemRepository.findAll();
        return allItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
    public Order saveOrder(Order order) {
        emailService.sendOrder(order);
        return orderRepository.save(order);
    }
}
