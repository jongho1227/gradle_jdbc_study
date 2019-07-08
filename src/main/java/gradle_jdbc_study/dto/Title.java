package gradle_jdbc_study.dto;

public class Title {
	private int tno;
	private String tname;
	
	public Title() {
	
	}

	public Title(int tno) {
		this.tno = tno;
	}

	public Title(int tno, String tname) {
		this.tno = tno;
		this.tname = tname;
	}

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tno;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Title other = (Title) obj;
		if (tno != other.tno)
			return false;
		return true;
	}
	
	public Object[] toArray() {
		
		return new Object[]{String.format("D%03d", tno), tname};
	}

	@Override
	public String toString() {
		return String.format("%s", tname);
	}
	
	
	
}
