package dao;

import java.util.List;

import table.Score;

public interface ScoreDao {
	public int createScore(Score score);
	public List<Score> scoreList(Score score);
	public List<Score> scoreList(int sid);
}
