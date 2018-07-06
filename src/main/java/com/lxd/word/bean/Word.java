package com.lxd.word.bean;

import java.util.Date;

public class Word {
    private Integer id;

    private String belong;

    private Integer type;

    private String origin;

    private String end;

    private Date inserttime;

    private Integer collect;

    private String remark1;

    private String remark2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong == null ? null : belong.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end == null ? null : end.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

	public Word(Integer id, String belong, Integer type, String origin, String end, Date inserttime, Integer collect,
			String remark1, String remark2) {
		super();
		this.id = id;
		this.belong = belong;
		this.type = type;
		this.origin = origin;
		this.end = end;
		this.inserttime = inserttime;
		this.collect = collect;
		this.remark1 = remark1;
		this.remark2 = remark2;
	}

	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Word [id=" + id + ", belong=" + belong + ", type=" + type + ", origin=" + origin + ", end=" + end
				+ ", inserttime=" + inserttime + ", collect=" + collect + ", remark1=" + remark1 + ", remark2="
				+ remark2 + "]";
	}
    
}