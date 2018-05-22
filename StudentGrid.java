package net.ijoha;

class StudentGrid
{
    public  StudentGrid (String fname, String lname, String email, String gender)
    {
        this.fname= fname;
        this.lname= lname;
        this.email= email;
        this.gender= gender;
    }
    private String fname;
    private String lname;
    private String email;
    private String gender;
	/**
	 * @return the fname
	 */
	public String getFName() {
		return fname;
	}
	/**
	 * @param fname the fname to set
	 */
	public void setFName(String fname) {
		this.fname = fname;
	}
	/**
	 * @return the lname
	 */
	public String getLName() {
		return lname;
	}
	/**
	 * @param fname the lname to set
	 */
	public void setLName(String lname) {
		this.lname = lname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gnder the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
}