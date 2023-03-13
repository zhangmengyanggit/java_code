<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="tittle">
        <el-input
          v-model="queryParams.tittle"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="政策级别" prop="policyLevel">
        <el-select v-model="queryParams.policyLevel" placeholder="请选择政策级别" clearable>
          <el-option>
            请选择
          </el-option>
          <el-option
            v-for="dict in dict.type.rank"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="适用行业" prop="applicableIndustries">
        <el-select v-model="queryParams.applicableIndustries" placeholder="请选择适用行业" clearable>
          <el-option>
              请选择
            </el-option>
          <el-option
            v-for="dict in dict.type.applicable_industries"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间">
        <el-date-picker
          v-model="daterangePublishTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="发布人" prop="publisher">
        <el-input
          v-model="queryParams.publisher"
          placeholder="请输入发布人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="企业规模" prop="enterpriseSize">
        <el-input
          v-model="queryParams.enterpriseSize"
          placeholder="请输入企业规模"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['scienceandtechnology:originalpolicy:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['scienceandtechnology:originalpolicy:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['scienceandtechnology:originalpolicy:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['scienceandtechnology:originalpolicy:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="originalpolicyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标题" width="600" align="center" prop="tittle" />
      <el-table-column label="政策级别" align="center" prop="policyLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.rank" :value="scope.row.policyLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="发布部门" align="center" prop="publishingDepartment" />
      <el-table-column label="适用行业" width="120" align="center" prop="applicableIndustries">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.applicable_industries" :value="scope.row.applicableIndustries"/>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="展示状态" align="center" prop="state" />
      <el-table-column label="发布人" align="center" prop="publisher" />
      <el-table-column label="项目状态" align="center" prop="itemStatus" />
      <el-table-column label="标签" align="center" prop="tagIds" />
      <el-table-column label="企业规模" align="center" prop="enterpriseSize" />
      <el-table-column label="发布状态" align="center" prop="publishStatus" />
      <el-table-column label="操作"  width="150" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
           <el-button   v-if="scope.row.publishStatus == 0"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['scienceandtechnology:originalpolicy:edit']"
            >解读</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['scienceandtechnology:originalpolicy:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['scienceandtechnology:originalpolicy:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 添加或修改政策管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="tittle">
          <el-input v-model="form.tittle" placeholder="请输入标题" type="textarea"/>
        </el-form-item>
        <el-form-item label="政策原文内容" prop="content">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="政策级别" prop="policyLevel">
          <el-select v-model="form.policyLevel" placeholder="请选择政策级别">
             <el-option>
                请选择
              </el-option>
            <el-option 
              v-for="dict in dict.type.rank"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="适用行业" prop="applicableIndustries">
          <el-select v-model="form.applicableIndustries" placeholder="请选择适用行业">
             <el-option>
                请选择
              </el-option>
            <el-option
              v-for="dict in dict.type.applicable_industries"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
       <el-form-item label="展示状态" prop="state">
            <el-select v-model="form.state" placeholder="请选择展示状态">
               <el-option>
                请选择
              </el-option>
              <el-option
                v-for="dict in dict.type.show_state"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        <el-form-item label="资料上传" prop="meansUrl">
          <file-upload v-model="form.meansUrl"/>
        </el-form-item>
        <el-form-item label="企业规模" prop="enterpriseSize">
          <el-input v-model="form.enterpriseSize" placeholder="请输入企业规模" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOriginalpolicy, getOriginalpolicy, delOriginalpolicy, addOriginalpolicy, updateOriginalpolicy } from "@/api/scienceandtechnology/originalpolicy/originalpolicy";

export default {
  name: "Originalpolicy",
  dicts: ['rank', 'applicable_industries','show_state'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 政策管理表格数据
      originalpolicyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 发布状态时间范围
      daterangePublishTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tittle: null,
        policyLevel: null,
        publishingDepartment: null,
        applicableIndustries: null,
        publishTime: null,
        state: null,
        publisher: null,
        itemStatus: null,
        enterpriseSize: null,
        publishStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tittle: [
          { required: true, message: "标题不能为空", trigger: "change" }
        ],
        applicableIndustries: [
          { required: true, message: "适用行业不能为空", trigger: "change" }
        ],
        policyLevel: [
          { required: true, message: "政策级别不能为空", trigger: "change" }
        ], 
        state: [
          { required: true, message: "展示状态不能为空", trigger: "change" }
        ],
        tagIds: [
          { required: true, message: "标签id集合不能为空", trigger: "change" }
        ],
        publishStatus: [
          { required: true, message: "发布状态不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询政策管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangePublishTime && '' != this.daterangePublishTime) {
        this.queryParams.params["beginPublishTime"] = this.daterangePublishTime[0];
        this.queryParams.params["endPublishTime"] = this.daterangePublishTime[1];
      }
      listOriginalpolicy(this.queryParams).then(response => {
        this.originalpolicyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        tittle: null,
        content: null,
        policyLevel: null,
        publishingDepartment: null,
        policyCategory: null,
        applicableIndustries: null,
        publishTime: null,
        state: null,
        createTime: null,
        createBy: null,
        appendixUrl: null,
        publishUnit: null,
        publisher: null,
        meansUrl: null,
        itemStatus: null,
        tagIds: null,
        enterpriseSize: null,
        publishStatus: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangePublishTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加政策管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOriginalpolicy(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改政策管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOriginalpolicy(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOriginalpolicy(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除政策管理编号为"' + ids + '"的数据项？').then(function() {
        return delOriginalpolicy(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('scienceandtechnology/originalpolicy/export', {
        ...this.queryParams
      }, `originalpolicy_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
