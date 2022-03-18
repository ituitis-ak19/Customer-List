package com.ibt.userList.Model;

import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;

@Table
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel(value = "Customer Api model documentation", description = "Model")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique id field of customer object")
    private Integer id;
    @ApiModelProperty(value = "Name field of customer object")
    private String name;
    @ApiModelProperty(value = "Surname field of customer object")
    private String surname;

    }
