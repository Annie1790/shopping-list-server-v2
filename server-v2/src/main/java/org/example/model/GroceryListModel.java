package org.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GroceryListModel {
    @Id
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private boolean isCompleted;

}
