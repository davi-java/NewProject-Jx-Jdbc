package model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO Department " + "(Name)" + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, department.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
				System.out.println("Creat Sucess Department: " + department);
			} else {
				throw new DbException("No lines Affected");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement((Statement) st);
		}

	}

	@Override
	public void update(Department department) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE Department " + "SET Name = ? " + "WHERE Id = ?");
			st.setString(1, department.getName());
			st.setInt(2, department.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Department Sucess Update ");
			} else {
				throw new DbException("Error in Update!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement((Statement) st);
		}

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE FROM Department WHERE Id = ? ");
			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Delete Sucess Department");
			} else {
				throw new DbException("Error in Delete Department");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement((Statement) st);
		}
	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM Department WHERE Id = ? ");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Department department = new Department(rs.getInt("Id"), rs.getString("Name"));
				return department;
			} else {
				throw new DbException("Error Id nonexistent! ");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement((Statement) st);
		}
	}

	@Override
	public List<Department> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM Department ");
			
			rs = st.executeQuery();
			List<Department> listDepartments = new ArrayList<>();
			
			while (rs.next()) {
				listDepartments.add(instantionDepartment(rs));				
			}

			return listDepartments;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement((Statement) st);
		}
	}

	private Department instantionDepartment(ResultSet rs) {

		try {
			int id = rs.getInt("Id");
			String name = rs.getString("Name");
			Department department = new Department(id, name);

			return department;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
}
