package gradle_jdbc_study;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.daoImpl.TitleDaoImpl;
import gradle_jdbc_study.dto.Title;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TitleDaoTest {
	static TitleDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		dao = new TitleDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao=null;
	}

	@Test
	public void test01SelectTitleByAll() {
		List<Title> lists = dao.selectTitleByAll();
		
		Assert.assertNotEquals(0, lists.size());
	}
	@Test
	public void test02SelectTitleByNo() throws SQLException {
		Title title = new Title(1);
		Title selTitle = dao.selectTitleByNo(title);
		Assert.assertNotNull(selTitle);
	}
	@Test
	public void test03InsertTitle() throws SQLException {
		Title title = new Title(6,"신입");
		int res = dao.insertTitle(title);
		Assert.assertNotEquals(-1, res);
		
	}
	@Test
	public void test04UpdateTitle() throws SQLException {
		Title title = new Title(6,"초보");
		int res = dao.updateTitle(title);
		Assert.assertNotEquals(-1, res);
		
	}
	@Test
	public void test05DeleteTitle() throws SQLException {
		Title title = new Title(6);
		int res = dao.deleteTitle(title);
		Assert.assertNotEquals(-1, res);
		
	}
	
}
