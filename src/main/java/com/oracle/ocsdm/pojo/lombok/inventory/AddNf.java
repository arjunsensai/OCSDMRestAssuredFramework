package com.oracle.ocsdm.pojo.lombok.inventory;

public class AddNf {

    public class Group {
        private String ipAddress;
        private String name;
        private String parentGroupId;
        private String id;
        private String parentGroupFullName;
        public String getIpAddress() {
            return ipAddress;
        }
        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getParentGroupId() {
            return parentGroupId;
        }
        public void setParentGroupId(String parentGroupId) {
            this.parentGroupId = parentGroupId;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getParentGroupFullName() {
            return parentGroupFullName;
        }
        public void setParentGroupFullName(String parentGroupFullName) {
            this.parentGroupFullName = parentGroupFullName;
        }
    }
    public class Groups {
        private Group group;
        public Group getGroup() {
            return group;
        }
        public void setGroup(Group group) {
            this.group = group;
        }
    }

    public class Nf {
        private String groupType;
        private Boolean hidden;
        private String name;
        private NfType nfType;
        private Groups groups;
        private NfCategory nfCategory;
        private String parentGroupId;
        private String id;
        private String parentGroupFullName;
        public String getGroupType() {
            return groupType;
        }
        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }
        public Boolean getHidden() {
            return hidden;
        }
        public void setHidden(Boolean hidden) {
            this.hidden = hidden;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public NfType getNfType() {
            return nfType;
        }
        public void setNfType(NfType nfType) {
            this.nfType = nfType;
        }
        public Groups getGroups() {
            return groups;
        }
        public void setGroups(Groups groups) {
            this.groups = groups;
        }
        public NfCategory getNfCategory() {
            return nfCategory;
        }
        public void setNfCategory(NfCategory nfCategory) {
            this.nfCategory = nfCategory;
        }
        public String getParentGroupId() {
            return parentGroupId;
        }
        public void setParentGroupId(String parentGroupId) {
            this.parentGroupId = parentGroupId;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getParentGroupFullName() {
            return parentGroupFullName;
        }
        public void setParentGroupFullName(String parentGroupFullName) {
            this.parentGroupFullName = parentGroupFullName;
        }
    }
    public class NfCategory {
        private String product;
        private String vendor;
        private String name;
        private String id;
        public String getProduct() {
            return product;
        }
        public void setProduct(String product) {
            this.product = product;
        }
        public String getVendor() {
            return vendor;
        }
        public void setVendor(String vendor) {
            this.vendor = vendor;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }

    public class NfType {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    public class Root {
        private Nf nf;
        public Nf getNf() {
            return nf;
        }
        public void setNf(Nf nf) {
            this.nf = nf;
        }
    }


}
