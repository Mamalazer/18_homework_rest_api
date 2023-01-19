package reqres.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResource {

    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;
}
