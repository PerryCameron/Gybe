package com.ecsail.Gybe.repository.interfaces;

public interface GeneralRepository {
    boolean recordExists(String tableName, String columnName, int value);
}
