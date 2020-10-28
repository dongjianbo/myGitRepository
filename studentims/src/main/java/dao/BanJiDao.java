package dao;

import java.util.List;

import table.BanJi;

public interface BanJiDao {
	/*
	 * 获取所有的班级信息
	 */
	public List<BanJi> getBanJi();
	/**
	 * 插入班级
	 */
	public int insertBanJi(BanJi bj);
	/**
	 * 修改班级
	 */
	public int updateBanJi(BanJi bj);
	/**
	 * 删除班级
	 */
	public int deleteBanJi(int bjid);
	/**
	 * 按照班级名称查找ID
	 */
	public int getIdByName(String bjName);
}
