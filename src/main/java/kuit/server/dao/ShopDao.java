package kuit.server.dao;

import kuit.server.dto.shop.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ShopDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ShopDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public long createShop(Shop shop) {
        String sql = "INSERT INTO Shop (shop_name, shop_call_num, is_open_now, address, food_category) " +
                "VALUES (:shopName, :shopCallNum, :isOpenNow, :address, :foodCategory)";

        Map<String, Object> paramMap = Map.of(
                "shopName", shop.getShopName(),
                "shopCallNum", shop.getShopCallNum(),
                "isOpenNow", shop.isOpenNow(),
                "address", shop.getAddress(),
                "foodCategory", shop.getFoodCategory()
        );

        return namedParameterJdbcTemplate.update(sql, paramMap);
    }



    public List<Shop> getShopsByAddress(String address) {
        String sql = "SELECT * FROM Shop WHERE address = :address";
        Map<String, Object> paramMap = Collections.singletonMap("address", address);

        return namedParameterJdbcTemplate.query(sql, paramMap, (rs, rowNum) -> {
            Shop shop = new Shop();
            shop.setShopId(rs.getLong("Shop_id"));
            shop.setShopName(rs.getString("shop_name"));
            shop.setShopCallNum(rs.getString("shop_call_num"));
            shop.setOpenNow(rs.getBoolean("is_open_now"));
            shop.setAddress(rs.getString("address"));
            shop.setFoodCategory(rs.getString("food_category"));
            return shop;
        });
    }

    public List<Shop> getAllShops() {
        String sql = "SELECT * FROM Shop";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Shop shop = new Shop();
            shop.setShopId(rs.getLong("Shop_id"));
            shop.setShopName(rs.getString("shop_name"));
            shop.setShopCallNum(rs.getString("shop_call_num"));
            shop.setOpenNow(rs.getBoolean("is_open_now"));
            shop.setAddress(rs.getString("address"));
            shop.setFoodCategory(rs.getString("food_category"));
            return shop;
        });
    }

    public List<Shop> getShopById(long shopId) {
        String sql = "SELECT * FROM Shop WHERE Shop_id = :shopId";
        Map<String, Object> paramMap = Collections.singletonMap("shopId", shopId);

        return namedParameterJdbcTemplate.query(sql, paramMap, (rs, rowNum) -> {
            Shop shop = new Shop();
            shop.setShopId(rs.getLong("Shop_id"));
            shop.setShopName(rs.getString("shop_name"));
            shop.setShopCallNum(rs.getString("shop_call_num"));
            shop.setOpenNow(rs.getBoolean("is_open_now"));
            shop.setAddress(rs.getString("address"));
            shop.setFoodCategory(rs.getString("food_category"));
            return shop;
        });
    }
    public List<Shop> getShopsByCategory(String category) {
        String sql = "SELECT * FROM Shop WHERE food_category = :category";
        Map<String, Object> paramMap = Collections.singletonMap("category", category);

        return namedParameterJdbcTemplate.query(sql, paramMap, (rs, rowNum) -> mapRowToShop(rs));
    }

    public List<Shop> getShopsByCategoryAndAddress(String category, String address) {
        String sql = "SELECT * FROM Shop WHERE food_category = :category AND address = :address";
        Map<String, Object> paramMap = Map.of("category", category, "address", address);

        return namedParameterJdbcTemplate.query(sql, paramMap, (rs, rowNum) -> mapRowToShop(rs));
    }
    private Shop mapRowToShop(ResultSet rs) throws SQLException {
        Shop shop = new Shop();
        shop.setShopId(rs.getLong("Shop_id"));
        shop.setShopName(rs.getString("shop_name"));
        shop.setShopCallNum(rs.getString("shop_call_num"));
        shop.setOpenNow(rs.getBoolean("is_open_now"));
        shop.setAddress(rs.getString("address"));
        shop.setFoodCategory(rs.getString("food_category"));
        return shop;
    }

    public List<FoodCategory> getAllFoodCategories() {
        String sql = "SELECT food_category FROM food_category";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setCategory(rs.getString("food_category"));
            return foodCategory;
        });
    }

}
