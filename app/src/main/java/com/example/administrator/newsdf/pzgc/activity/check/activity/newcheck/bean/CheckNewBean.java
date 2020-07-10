package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean;

public class CheckNewBean {
    public static class permission {
        //   是否有提交权限{false:否；true:是}
        boolean submitButton;
        // 是否有编辑权限{false:否；true:是}
        boolean editButton;
        //是否有打回权限{false:否；true:是}
        boolean returnButton;
        // 是否有确认权限{false:否；true:是}
        boolean confirmButton;

        public boolean isSubmitButton() {
            return submitButton;
        }

        public void setSubmitButton(boolean submitButton) {
            this.submitButton = submitButton;
        }

        public boolean isEditButton() {
            return editButton;
        }

        public void setEditButton(boolean editButton) {
            this.editButton = editButton;
        }

        public boolean isReturnButton() {
            return returnButton;
        }

        public void setReturnButton(boolean returnButton) {
            this.returnButton = returnButton;
        }

        public boolean isConfirmButton() {
            return confirmButton;
        }

        public void setConfirmButton(boolean confirmButton) {
            this.confirmButton = confirmButton;
        }
    }

    public static class scorePane {
        //检查细项id
        String id;
        //是否下整改{1：否；2：是}
        Integer generate;
        //标准分
        String standardScore;
        //得分
        String score;
        //管理行为扣分（值为负数，小于0则被扣分）
        Integer checkScore;
        String checkGrade;

        public String getCheckGrade() {
            return checkGrade;
        }

        public void setCheckGrade(String checkGrade) {
            this.checkGrade = checkGrade;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getGenerate() {
            return generate;
        }

        public void setGenerate(Integer generate) {
            this.generate = generate;
        }

        public String getStandardScore() {
            return standardScore;
        }

        public void setStandardScore(String standardScore) {
            this.standardScore = standardScore;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public Integer getCheckScore() {
            return checkScore;
        }

        public void setCheckScore(Integer checkScore) {
            this.checkScore = checkScore;
        }
    }
}
