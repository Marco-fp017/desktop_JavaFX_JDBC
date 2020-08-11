package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into department "
					+ "(Name) "
					+ "value (?)",
					st.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					obj.setId(rs.getInt(1));
					DB.closeResultSet(rs);
				}
				else throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"update department "
					+ "set Name = ? "
					+ "where Id = ?");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}		
	}

	@Override
	public void delete(Department obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"delete from department "
					+ "where Id = ?");
			
			st.setInt(1, obj.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select Name "
					+ "from department "
					+ "where Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dep = new Department(id, rs.getString(1));
				return dep;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Department> depList = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("select * from department");
			rs = st.executeQuery();

			while(rs.next()) {
				Department dep = new Department (rs.getInt(1), rs.getString(2));
				
				depList.add(dep);
			}
			return depList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}			
	}

}
