package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfDataTest {
    private Integer column1;
 
    private String column2;
 
    private String column3;
 
    private String column4;
 
    private String column5;
 
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PdfDataTest{");
        sb.append("column1=").append(column1);
        sb.append(", column2='").append(column2).append('\'');
        sb.append(", column3='").append(column3).append('\'');
        sb.append(", column4='").append(column4).append('\'');
        sb.append(", column5='").append(column5).append('\'');
        sb.append('}');
        return sb.toString();
    }
}