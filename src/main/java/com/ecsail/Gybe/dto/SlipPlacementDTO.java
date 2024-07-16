package com.ecsail.Gybe.dto;

public class SlipPlacementDTO {

    String dock;
    int tablePlacedTo;
    int orderPlaced;
    int pagePlaced;

    public SlipPlacementDTO(String dock, int tablePlacedTo, int orderPlaced, int pagePlaced) {
        this.dock = dock;
        this.tablePlacedTo = tablePlacedTo;
        this.orderPlaced = orderPlaced;
        this.pagePlaced = pagePlaced;
    }

    public String getDock() {
        return dock;
    }

    public void setDock(String dock) {
        this.dock = dock;
    }

    public int getTablePlacedTo() {
        return tablePlacedTo;
    }

    public void setTablePlacedTo(int tablePlacedTo) {
        this.tablePlacedTo = tablePlacedTo;
    }

    public int getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(int orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public int getPagePlaced() {
        return pagePlaced;
    }

    public void setPagePlaced(int pagePlaced) {
        this.pagePlaced = pagePlaced;
    }

    @Override
    public String toString() {
        return "SlipPlacementDTO{" +
                "dock='" + dock + '\'' +
                ", tablePlacedTo=" + tablePlacedTo +
                ", orderPlaced=" + orderPlaced +
                ", pagePlaced=" + pagePlaced +
                '}';
    }
}
