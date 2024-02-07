package org.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("grocery_list")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroceryItem {
    @Id
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private boolean isCompleted;



}
