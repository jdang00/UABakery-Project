/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessObjects;

/**
 *
 * @author justindang
 */
import java.util.List;

public interface UABakeryInterface<UATYPE> {
    
    public List<UATYPE> getAllItemsFromDatabase();

    public List<UATYPE> getItem(String key);

    public void update(UATYPE item);

    public void delete(UATYPE item);

    public void insert(UATYPE item);
    
}
