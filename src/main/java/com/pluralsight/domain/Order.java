package com.pluralsight.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Order {

    private final List<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        if (item != null) {
            items.add(item);
        }
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
    }

    public List<MenuItem> query(Predicate<MenuItem> filter,
                                Comparator<MenuItem> sorter) {
        Predicate<MenuItem> actualFilter =
                (filter == null) ? it -> true : filter;
        Comparator<MenuItem> actualSorter =
                (sorter == null) ? Comparator.comparing(MenuItem::getLabel) : sorter;

        return items.stream()
                .filter(actualFilter)
                .sorted(actualSorter)
                .collect(Collectors.toList());
    }

    public List<String[]> toRows(List<MenuItem> list) {
        List<String[]> rows = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            MenuItem m = list.get(i);
            rows.add(new String[] {
                    m.getCategory(),
                    m.getLabel(),
                    String.format("$%.2f", m.getPrice())
            });
        }
        return rows;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order Details:\n");
        for (MenuItem item : items) {
            sb.append(" - ").append(item).append("\n");
        }
        sb.append("Total: $").append(String.format("%.2f", calculateTotal()));
        return sb.toString();
    }
}
