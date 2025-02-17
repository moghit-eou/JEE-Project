package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.Connection_jdbc;
import com.denbondd.restaurant.db.ReceiptDao;
import com.denbondd.restaurant.db.entity.Receipt;
import com.denbondd.restaurant.db.entity.Status;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlReceiptDao implements ReceiptDao {

    private static Receipt mapReceipt(ResultSet rs) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(rs.getLong("id"));
        receipt.setUserId(rs.getLong("user_id"));
        receipt.setStatus(Status.getStatusById(rs.getLong("status_id")));
        receipt.setTotal(rs.getInt("total"));
        receipt.setManagerId(rs.getLong("manager_id"));
        receipt.setCreateDate(rs.getTimestamp("create_date"));
        return receipt;
    }

    @Override
    public List<Receipt> getUserReceipts(long userId) throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ( new Connection_jdbc()).getConnection()
        )
        {

            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_USER_RECEIPTS);

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = mapReceipt(rs);
                    receipt.setDishes(getReceiptDishes(receipt.getId()));
                    receipts.add(receipt);
                }
            }
            return receipts;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getUserReceipts", e);
        }
    }

    private List<Receipt.Dish> getReceiptDishes(long receiptId) throws SQLException {
        List<Receipt.Dish> dishes = new ArrayList<>();
        try (Connection c = ( new Connection_jdbc()).getConnection() )
        {
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_RECEIPT_DISHES);
            ps.setLong(1, receiptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt.Dish dish = new Receipt.Dish();
                    dish.setId(rs.getLong("id"));
                    dish.setName(rs.getString("name"));
                    dish.setPrice(rs.getInt("price"));
                    dish.setCount(rs.getInt("count"));
                    dishes.add(dish);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Cannot getReceiptDishes");
        }
        return dishes;
    }

    @Override
    public List<Receipt> getAllReceipts() throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ( new Connection_jdbc()).getConnection()
        )
        {
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_RECEIPTS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Receipt receipt = mapReceipt(rs);
                receipt.setDishes(getReceiptDishes(receipt.getId()));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getAllReceipts", e);
        }
    }

    @Override
    public List<Receipt> getAllReceiptsAcceptedBy(long managerId) throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try ( Connection c = ( new Connection_jdbc()).getConnection() )
        {
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_RECEIPTS_APPROVED_BY);
            ps.setLong(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = mapReceipt(rs);
                    receipt.setDishes(getReceiptDishes(receipt.getId()));
                    receipts.add(receipt);
                }
            }
            return receipts;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getAllReceiptsAcceptedBy", e);
        }
    }

    @Override
    public List<Receipt> getNotApproved() throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try ( Connection c = ( new Connection_jdbc()).getConnection())
        {
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_NOT_APPROVED);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Receipt receipt = mapReceipt(rs);
                receipt.setDishes(getReceiptDishes(receipt.getId()));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getNotApproved", e);
        }
    }

    @Override
    public void changeStatus(long receiptId, long statusId, long managerId) throws DbException {
        try (Connection c = ( new Connection_jdbc()).getConnection()
        ) {
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(SqlUtils.CHANGE_RECEIPT_STATUS);
            int k = 0;
            ps.setLong(++k, statusId);
            ps.setLong(++k, managerId);
            ps.setLong(++k, receiptId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Change status failed, no rows attached");
            }
            c.commit();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot changeStatus", e);
        }
    }
}
