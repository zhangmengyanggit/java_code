<template>
  <el-dialog
    width="700px"
    :close-on-click-modal="false"
    title="选择企业"
    :visible.sync="visible"
    append-to-body
    :before-close="handlerDialogClosed"
    @closed="handlerDialogClosed"
  >
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="企业名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入企业名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="enterpriseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="企业名称"  width="120" align="center" prop="name" />
      <!-- <el-table-column label="企业性质或控股类型" width="140" align="center" prop="enterpriseNature" >
        <template slot-scope="scope">
          <span v-for="item in dict.type.enterprise_nature" v-if="item.value== scope.row.enterpriseNature" :key="item" v-text="item.label"/>
        </template>
      </el-table-column> -->
      <el-table-column label="企业邮箱"  width="120" align="center" prop="enterpriseMailbox" />
      <el-table-column label="在职人数" align="center" prop="numberOfEmployees" />
      <el-table-column label="营业执照" align="center" prop="businessLicenseUrl">
        <template slot-scope="scope">
          <image-preview :src="scope.row.businessLicenseUrl" :width="50" :height="50" />
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <span slot="footer" class="dialog-footer">
      <el-button @click="handlerDialogClosed">取 消</el-button>
      <el-button type="primary" :disabled="!ids.length" @click="publish">确认发布</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { listEnterprise } from '@/api/scienceandtechnology/enterprise/enterprise';

export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    originalpolicyId: {
      // eslint-disable-next-line vue/require-prop-type-constructor
      type: String | Number,
      default: ''
    }
  },
  data() {
    return {
      // 总条数
      total: 0,
      // 企业表格数据
      enterpriseList: [],
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: ''
      },
    }
  },
  watch: {
    visible(val) {
      if (val) {
        this.getList();
      }
    }
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询企业列表 */
    getList() {
      this.loading = true;
      listEnterprise(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.enterpriseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handlerDialogClosed() {
      this.$emit('update:visible', false);
    },
    publish() {
      console.log(this.originalpolicyId, this.ids);
    }
  }
};
</script>
