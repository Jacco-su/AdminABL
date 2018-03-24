package com.dream.util;
import java.util.Comparator;

public class ScoreComparator implements Comparator<Double> {
	public int type=1;
	public int compare(Double score1, Double score2) {
		// TODO Auto-generated method stub
		//按照时间降序排列
		Double value1=score1;
		Double value2=score2;
		double result=value1.doubleValue()-value2.doubleValue();
		if(type==1){
			if(result>0){
				return -1;
			}else if(result<0){
				return 1;
			}else{
				return 0;
			}
		}else{
			if(result>0){
				return 1;
			}else if(result<0){
				return -1;
			}else{
				return 0;
			}
		}
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
