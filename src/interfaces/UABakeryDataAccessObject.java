/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author justindang
 */
import java.util.ArrayList;

public interface UABakeryDataAccessObject<UATYPE> {
    
    public ArrayList<UATYPE> getItems();

    public void delete(int id);

    public void insert(UATYPE item);

    
}
