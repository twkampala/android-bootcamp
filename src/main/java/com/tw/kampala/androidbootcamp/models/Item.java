package com.tw.kampala.androidbootcamp.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

@DatabaseTable(tableName = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private Integer uuid;
    @DatabaseField
    private String name;
    @DatabaseField
    private String city;
    @DatabaseField
    private String company;
    @DatabaseField
    private String email;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String description;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] imageBytes;

}
