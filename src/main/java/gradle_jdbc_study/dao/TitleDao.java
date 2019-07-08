package gradle_jdbc_study.dao;

import java.sql.SQLException;
import java.util.List;

import gradle_jdbc_study.dto.Title;

public interface TitleDao {
	List<Title> selectTitleByAll();
	Title selectTitleByNo(Title title) throws SQLException;
	int insertTitle(Title title) throws SQLException ;
	int deleteTitle(Title title) throws SQLException;
	int updateTitle(Title title) throws SQLException;
}
