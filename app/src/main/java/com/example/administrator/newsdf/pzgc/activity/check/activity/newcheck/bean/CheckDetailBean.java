package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean;

import java.util.ArrayList;
import java.util.List;

public class CheckDetailBean {
    //项目
    public static class Project {
        // 标段标准分
        String bStandardScore;
        //   标段检查标准
        String bCheckStandard;
        // 标准自评分
        String bScore;
        //   标段位置
        String bPosition;
        // 标段描述
        String bDescription;
        //  标段附件
        String bAttachment;
        //   标段是否生成整改通知单
        String bGenerate;
        // 检查覆盖级别{集团：覆盖1；分公司：覆盖1、2；标段：覆盖1、2、3}
        String checkGrade;
        //  对标段管理行为扣分
        String bCheckScore;
        //标段整改通知单
        String bNoticeId;
        //  分公司整改通知单
        String fNoticeId;
        //集团整改通知单
        String jNoticeId;

        private List<BFileListBean> bFileList;

        public List<BFileListBean> getBFileList() {
            return bFileList;
        }

        public void setBFileList(List<BFileListBean> bFileList) {
            this.bFileList = bFileList;
        }

        public class BFileListBean {
            /**
             * fileext : jpg
             * filename : tiny-864-2020-07-07-06-35-15
             * filepath : upload/2020/07/07/19912533aa81469da256b16ddf695127.jpg
             * filesize : 2010
             * id : 3fac97d2dce745c3a53702e89e244594
             */

            private String fileext;
            private String filename;
            private String filepath;
            private String id;

            public String getFileext() {
                return fileext;
            }

            public void setFileext(String fileext) {
                this.fileext = fileext;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getFilepath() {
                return filepath;
            }

            public void setFilepath(String filepath) {
                this.filepath = filepath;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }


        public String getbStandardScore() {
            return bStandardScore;
        }

        public void setbStandardScore(String bStandardScore) {
            this.bStandardScore = bStandardScore;
        }

        public String getbCheckStandard() {
            return bCheckStandard;
        }

        public void setbCheckStandard(String bCheckStandard) {
            this.bCheckStandard = bCheckStandard;
        }

        public String getbScore() {
            return bScore;
        }

        public void setbScore(String bScore) {
            this.bScore = bScore;
        }

        public String getbPosition() {
            return bPosition;
        }

        public void setbPosition(String bPosition) {
            this.bPosition = bPosition;
        }

        public String getbDescription() {
            return bDescription;
        }

        public void setbDescription(String bDescription) {
            this.bDescription = bDescription;
        }

        public String getbAttachment() {
            return bAttachment;
        }

        public void setbAttachment(String bAttachment) {
            this.bAttachment = bAttachment;
        }

        public String getbGenerate() {
            return bGenerate;
        }

        public void setbGenerate(String bGenerate) {
            this.bGenerate = bGenerate;
        }

        public String getCheckGrade() {
            return checkGrade;
        }

        public void setCheckGrade(String checkGrade) {
            this.checkGrade = checkGrade;
        }

        public String getbCheckScore() {
            return bCheckScore;
        }

        public void setbCheckScore(String bCheckScore) {
            this.bCheckScore = bCheckScore;
        }

        public String getbNoticeId() {
            return bNoticeId;
        }

        public void setbNoticeId(String bNoticeId) {
            this.bNoticeId = bNoticeId;
        }

        public String getfNoticeId() {
            return fNoticeId;
        }

        public void setfNoticeId(String fNoticeId) {
            this.fNoticeId = fNoticeId;
        }

        public String getjNoticeId() {
            return jNoticeId;
        }

        public void setjNoticeId(String jNoticeId) {
            this.jNoticeId = jNoticeId;
        }


    }

    //公司
    public static class Company {
        //  分公司标准分
        String fStandardScore;
        //分公司检查标准
        String fCheckStandard;
        //  分公司自评分
        String fScore;
        // 分公司位置
        String fPosition;
        //  分公司描述
        String fDescription;
        //  分公司附件
        String fAttachment;
        // 分公司是否生成整改通知单
        String fGenerate;
        //  对分公司管理行为扣分
        // 检查覆盖级别{集团：覆盖1；分公司：覆盖1、2；标段：覆盖1、2、3}
        String checkGrade;
        String fCheckScore;
        String bCheckScore;
        private List<FFileListBean> FFileList;

        public List<FFileListBean> getFFileList() {
            return FFileList;
        }

        public void setFFileList(List<FFileListBean> FFileList) {
            this.FFileList = FFileList;
        }

        public class FFileListBean {
            /**
             * fileext : jpg
             * filename : tiny-864-2020-07-07-06-35-15
             * filepath : upload/2020/07/07/19912533aa81469da256b16ddf695127.jpg
             * filesize : 2010
             * id : 3fac97d2dce745c3a53702e89e244594
             */

            private String fileext;
            private String filename;
            private String filepath;
            private String id;

            public String getFileext() {
                return fileext;
            }

            public void setFileext(String fileext) {
                this.fileext = fileext;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getFilepath() {
                return filepath;
            }

            public void setFilepath(String filepath) {
                this.filepath = filepath;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public String getbCheckScore() {
            return bCheckScore;
        }

        public void setbCheckScore(String bCheckScore) {
            this.bCheckScore = bCheckScore;
        }

        public String getfStandardScore() {
            return fStandardScore;
        }

        public void setfStandardScore(String fStandardScore) {
            this.fStandardScore = fStandardScore;
        }

        public String getfCheckStandard() {
            return fCheckStandard;
        }

        public void setfCheckStandard(String fCheckStandard) {
            this.fCheckStandard = fCheckStandard;
        }

        public String getfScore() {
            return fScore;
        }

        public void setfScore(String fScore) {
            this.fScore = fScore;
        }

        public String getfPosition() {
            return fPosition;
        }

        public void setfPosition(String fPosition) {
            this.fPosition = fPosition;
        }

        public String getfDescription() {
            return fDescription;
        }

        public void setfDescription(String fDescription) {
            this.fDescription = fDescription;
        }

        public String getfAttachment() {
            return fAttachment;
        }

        public void setfAttachment(String fAttachment) {
            this.fAttachment = fAttachment;
        }

        public String getfGenerate() {
            return fGenerate;
        }

        public void setfGenerate(String fGenerate) {
            this.fGenerate = fGenerate;
        }

        public String getfCheckScore() {
            return fCheckScore;
        }

        public void setfCheckScore(String fCheckScore) {
            this.fCheckScore = fCheckScore;
        }


        public String getCheckGrade() {
            return checkGrade;
        }

        public void setCheckGrade(String checkGrade) {
            this.checkGrade = checkGrade;
        }
    }

    //集团
    public static class Group {
        //    检查标准id
        String safetyScoreStandardId;
        //  监管内容
        String name;
        //   集团标准分
        String jStandardScore;
        // 集团检查标准
        String jCheckStandard;
        //集团自评分
        String jScore;
        //   集团位置
        String jPosition;
        //  集团描述
        String jDescription;
        //   集团附件
        String jAttachment;
        //   集团是否生成通知单
        String jGenerate;
        //对分公司扣分
        String fCheckScore;

        String checkGrade;

        public String getCheckGrade() {
            return checkGrade;
        }

        public void setCheckGrade(String checkGrade) {
            this.checkGrade = checkGrade;
        }

        private List<JFileListBean> JFileList;

        public List<JFileListBean> getJFileList() {
            return JFileList;
        }

        public void setJFileList(List<JFileListBean> JFileList) {
            this.JFileList = JFileList;
        }

        public class JFileListBean {
            /**
             * fileext : jpg
             * filename : tiny-864-2020-07-07-06-35-15
             * filepath : upload/2020/07/07/19912533aa81469da256b16ddf695127.jpg
             * filesize : 2010
             * id : 3fac97d2dce745c3a53702e89e244594
             */

            private String fileext;
            private String filename;
            private String filepath;
            private String id;

            public String getFileext() {
                return fileext;
            }

            public void setFileext(String fileext) {
                this.fileext = fileext;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getFilepath() {
                return filepath;
            }

            public void setFilepath(String filepath) {
                this.filepath = filepath;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public String getfCheckScore() {
            return fCheckScore;
        }

        public void setfCheckScore(String fCheckScore) {
            this.fCheckScore = fCheckScore;
        }

        public String getSafetyScoreStandardId() {
            return safetyScoreStandardId;
        }

        public void setSafetyScoreStandardId(String safetyScoreStandardId) {
            this.safetyScoreStandardId = safetyScoreStandardId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getjStandardScore() {
            return jStandardScore;
        }

        public void setjStandardScore(String jStandardScore) {
            this.jStandardScore = jStandardScore;
        }

        public String getjCheckStandard() {
            return jCheckStandard;
        }

        public void setjCheckStandard(String jCheckStandard) {
            this.jCheckStandard = jCheckStandard;
        }

        public String getjScore() {
            return jScore;
        }

        public void setjScore(String jScore) {
            this.jScore = jScore;
        }

        public String getjPosition() {
            return jPosition;
        }

        public void setjPosition(String jPosition) {
            this.jPosition = jPosition;
        }

        public String getjDescription() {
            return jDescription;
        }

        public void setjDescription(String jDescription) {
            this.jDescription = jDescription;
        }

        public String getjAttachment() {
            return jAttachment;
        }

        public void setjAttachment(String jAttachment) {
            this.jAttachment = jAttachment;
        }

        public String getjGenerate() {
            return jGenerate;
        }

        public void setjGenerate(String jGenerate) {
            this.jGenerate = jGenerate;
        }


    }
}
