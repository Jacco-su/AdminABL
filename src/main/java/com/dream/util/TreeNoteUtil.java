package com.dream.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: TreeNoteUtil.java
 * @Description:递归获取树节点集合
 * @date 2018-03-16 上午9:49
 */
public class TreeNoteUtil {
	/**
	 * 获取父节点菜单
	 * @param treesList 所有树菜单集合
	 * @return
	 */
	public final static List<MenuTree> getFatherNode(List<MenuTree> treesList){
		List<MenuTree> newTrees = new ArrayList<MenuTree>();
		for (MenuTree mt : treesList) {
			if (StringUtils.isEmpty(mt.getpId())||"null".equals(mt.getpId())) {//如果pId为空，则该节点为父节点
				//递归获取父节点下的子节点
				mt.setChildren(getChildrenNode(mt.getId(), treesList));
				newTrees.add(mt);
			}
		}
		return newTrees;
	}

	/**
	 * 递归获取子节点下的子节点
	 * @param pId 父节点的ID
	 * @param treesList 所有菜单树集合
	 * @return
	 */
	public final static List<MenuTree> getChildrenNode(String pId, List<MenuTree> treesList){
		List<MenuTree> newTrees = new ArrayList<MenuTree>();
		for (MenuTree mt : treesList) {
			if (StringUtils.isEmpty(mt.getpId())||"null".equals(mt.getpId())) continue;
			if(mt.getpId().equals(pId)){
				//递归获取子节点下的子节点，即设置树控件中的children
				mt.setChildren(getChildrenNode(mt.getId(), treesList));
				//设置树控件attributes属性的数据
				newTrees.add(mt);
			}
		}
		return newTrees;
	}

}
