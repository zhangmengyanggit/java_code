<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="tittle">
        <el-input v-model="queryParams.tittle" placeholder="请输入标题" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="政策级别" prop="policyLevel">
        <el-select v-model="queryParams.policyLevel" placeholder="请选择政策级别" clearable>
          <el-option value="">
            请选择
          </el-option>
          <el-option v-for="dict in dict.type.rank" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属产业" prop="applicableIndustries">
        <el-select v-model="queryParams.applicableIndustries" placeholder="请选择所属产业" clearable>
          <el-option value="">
            请选择
          </el-option>
          <el-option v-for="dict in dict.type.applicable_industries" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间">
        <el-date-picker v-model="daterangePublishTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="发布人" prop="publisher">
        <el-input v-model="queryParams.publisher" placeholder="请输入发布人" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>


      <el-form-item label="是否有效" prop="validStatus">
        <el-select v-model="queryParams.validStatus" placeholder="请选择是否有效" clearable>
          <el-option value="">请选择</el-option>
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>

      <el-form-item label="截止时间">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="申请兑现" prop="applyForCash">
        <el-select v-model="queryParams.applyForCash" placeholder="请选择是否已有申请兑现" clearable>
          <el-option value="">请选择</el-option>
          <el-option v-for="dict in dict.type.apply_for_cash" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['scienceandtechnology:originalpolicy:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['scienceandtechnology:originalpolicy:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['scienceandtechnology:originalpolicy:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['scienceandtechnology:originalpolicy:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="originalpolicyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标题" width="600" align="center" prop="tittle" />
      <el-table-column label="政策级别" align="center" prop="policyLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.rank" :value="scope.row.policyLevel" />
        </template>
      </el-table-column>
      <el-table-column label="所在部门" align="center" prop="publishingDepartment">
        <template slot-scope="scope">
          <span v-for="item in departmentList" v-text="item.deptName"
            v-if="item.deptId == scope.row.publishingDepartment"></span>
        </template>

      </el-table-column>
      <el-table-column label="所属产业" width="120" align="center" prop="applicableIndustries">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.applicable_industries" :value="scope.row.applicableIndustries" />
        </template>
      </el-table-column>
      <el-table-column label="是否有效" width="120" align="center" prop="validStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.validStatus" />
        </template>
      </el-table-column>

      <el-table-column label="截止时间" align="center" prop="validDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.validDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请兑现" align="center" prop="applyForCash" width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.apply_for_cash" :value="scope.row.applyForCash" />
        </template>
      </el-table-column>
      <el-table-column label="展示状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.show_state" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发布人" align="center" prop="publisher" />
      <el-table-column label="项目状态" align="center" prop="itemStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.item_state" :value="scope.row.itemStatus" />
        </template>

      </el-table-column>

      <el-table-column label="发布状态" align="center" prop="publishStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.publish_status" :value="scope.row.publishStatus" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.publishStatus !=2" size="mini" type="text" icon="el-icon-add"
            @click="handleInterpretation(scope.row)"
            v-hasPermi="['scienceandtechnology:originalpolicy:interpretation']">解读</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['scienceandtechnology:originalpolicy:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['scienceandtechnology:originalpolicy:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
    <!-- 添加或修改政策管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="标题" prop="tittle">
          <el-input v-model="form.tittle" placeholder="请输入标题" type="textarea" />
        </el-form-item>
        <el-form-item label="政策原文内容" prop="content">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="政策级别" prop="policyLevel">
          <el-select v-model="form.policyLevel" placeholder="请选择政策级别">
            <el-option value="">
              请选择
            </el-option>
            <el-option v-for="dict in dict.type.rank" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产业分类" prop="applicableIndustries">
          <el-select v-model="form.applicableIndustries" placeholder="请选择产业分类">
            <el-option value="">
              请选择
            </el-option>
            <el-option v-for="dict in dict.type.applicable_industries" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="展示状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择展示状态">
            <el-option value="">
              请选择
            </el-option>
            <el-option v-for="dict in dict.type.show_state" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="是否有效" prop="validStatus">
          <el-radio-group v-model="form.validStatus">
            <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="parseInt(dict.value)">{{
              dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="截止时间" prop="validDate">
          <el-date-picker clearable v-model="form.validDate" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择有效截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请兑现" prop="applyForCash">
          <el-radio-group v-model="form.applyForCash">
            <el-radio v-for="dict in dict.type.apply_for_cash" :key="dict.value" :label="parseInt(dict.value)">{{
              dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="兑现联系人" prop="redeemContact">
          <el-input v-model="form.redeemContact" placeholder="请输入兑现联系人" />
        </el-form-item>
        <el-form-item label="兑现人电话" prop="redeemContactPhone">
          <el-input v-model="form.redeemContactPhone" placeholder="请输入兑现联系人电话" />
        </el-form-item>
        <el-form-item label="行业" prop="trade">
          <el-select v-model="form.trade" placeholder="请选择行业">
            <el-option value="">
              请选择
            </el-option>
            <el-option v-for="dict in dict.type.trade" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在部门" prop="publishingDepartment">
          <el-select v-model="form.publishingDepartment" placeholder="请选择所在部门" v-on="{ focus: seeDepartment }">
            <el-option value="">
              请选择
            </el-option>
            <el-option v-for="dict in departmentList" :key="dict.deptId" :label="dict.deptName"
              :value="dict.deptId"></el-option>
          </el-select>
        </el-form-item>
        <el-row>
          <el-form-item label="注册归属地" prop="street">
            <el-select v-model="form.province" placeholder="请选择省" v-on="{ focus: seeProvince }" @change="seeCity(2)"
              style="width:25%">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in ProvinceList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="form.city" placeholder="请选择市" @change="seeDistinct(2)" style="width:25%">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in CityList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="form.region" placeholder="请选择区" style="width:25%" @change="seeStreet(2)">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in DistinctList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="form.street" placeholder="请选择街道" style="width:25%">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in streetList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-row>

        <el-form-item label="资料上传" prop="meansUrl">
          <file-upload v-model="form.meansUrl" />
        </el-form-item>

        <el-form-item label="政策标签" prop="tagIds">
          <el-select v-model="form.tagIds" placeholder="请选择政策标签" multiple style="width: -webkit-fill-available;">
            <el-option value="">
              请选择
            </el-option>
            <el-option v-for="item in tags" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 政策解读对话框 -->
    <el-dialog :title="titleInterpretation" :visible.sync="openInterpretation" width="600px" append-to-body>
      <el-form ref="formInterpretation" :model="formInterpretation" :rules="rulesInterpretation" label-width="110px">
        <el-form-item label="解读标题" prop="tittle">
          <el-input v-model="formInterpretation.tittle" placeholder="请输入标题" type="textarea" />
        </el-form-item>
        <el-form-item label="政策解读内容" prop="content">
          <editor v-model="formInterpretation.content" :min-height="192" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormInterpretation">确 定</el-button>
        <el-button @click="cancelInterpretation">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOriginalpolicy, getOriginalpolicy, delOriginalpolicy, addOriginalpolicy, updateOriginalpolicy, getTags, getInterpretationPolicy, getCity, getDepartment, addInterpretationPolicy , editInterpretationPolicy } from "@/api/scienceandtechnology/originalpolicy/originalpolicy";

export default {
  name: "Originalpolicy",
  dicts: ['rank', 'applicable_industries', 'show_state', 'item_state', 'publish_status', 'sys_normal_disable', 'trade', "apply_for_cash"],
  data() {
    return {
      CityList: null,
      ProvinceList: null,
      DistinctList: null,
      streetList: null,
      departmentList: null,
      //初始化标签
      tags: [],
      // 遮罩层
      loading: true,
      // 选中数组
      idsInterpretation: [],
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
      // 弹出层标题解读
      titleInterpretation: "",
      // 是否显示弹出层解读
      openInterpretation: false,
      // 发布状态时间范围
      daterangePublishTime: [],
      // 截止日期范围
      dateRange: [],
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
        publishStatus: null,
        validStatus: null,
        validDate: null,
        applyForCash: null
      },
      // 表单参数
      form: {},
      // 表单参数解读
      formInterpretation: {},
      // 表单校验
      rules: {
        street: [
          { required: true, message: "归属街道不能为空", trigger: "blur" }
        ],
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
        ],
        validDate: [
          { required: true, message: "政策截止时间不能为空", trigger: "change" }
        ],
        publishingDepartment: [
          { required: true, message: "所在部门不能为空", trigger: "change" }
        ],
      },
      // 表单校验解读
      rulesInterpretation: {
        tittle: [
          { required: true, message: "标题不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getTagList();
    this.seeDepartment()
  },
  methods: {
    getTagList() {
      getTags().then(response => {
        this.tags = response
      });
    },
    /** 查询政策管理列表 */
    getList() {

      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangePublishTime && '' != this.daterangePublishTime) {
        this.queryParams.params["beginPublishTime"] = this.daterangePublishTime[0];
        this.queryParams.params["endPublishTime"] = this.daterangePublishTime[1];
      }
      listOriginalpolicy(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
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
    // 取消按钮
    cancelInterpretation() {
      this.openInterpretation = false;
      this.resetInterpretation();
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
        publishStatus: null,
        tagIds: null,
        validStatus: 0,
        validDate: null,
        applyForCash: 0,
        redeemContact: null,
        redeemContactPhone: null,
        trade: null,
        province: null,
        city: null,
        region: null,
        street: null
      };
      this.resetForm("form");
    },
    // 表单重置解读
    resetInterpretation() {
      this.formInterpretation = {
        id: null,
        tittle: null,
        content: null,
        originalPolicyId:null
      };
      this.resetForm("formInterpretation");
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
      this.single = selection.length !== 1
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
      const array = [];
      getOriginalpolicy(id).then(response => {
        this.form = response.data;
        this.form.publishingDepartment = parseInt(this.form.publishingDepartment);
        if (this.form.province != null) {
          this.seeProvince();
        }
        if (this.form.city != null) {
          this.seeCity(1);
        }
        if (this.form.region != null) {
          this.seeDistinct(1);
        }
        if (this.form.street != null) {
          this.seeStreet(1);
        }
        if (this.form.publishingDepartment != null) {
          this.seeProvince();
        }

        this.tags.forEach(element => {
          if (this.form.tagIds != null && this.form.tagIds.indexOf(element.id) != -1) {

            array.push(element.id);
          }
        });

        this.form.tagIds = array;
        console.log(this.form)
        this.open = true;
        this.title = "修改政策管理";
      });
    },
    /** 政策解读按钮操作 */
    handleInterpretation(row) {
      this.resetInterpretation();
      const id = row.id || this.idsInterpretation
      getInterpretationPolicy(id).then(response => {
        this.formInterpretation = response.data;
        this.formInterpretation.originalPolicyId= id;
        this.openInterpretation = true;
        this.titleInterpretation = "政策解读";
      });

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          //处理数组转string
          if (this.form.tagIds.length > 0) {

            this.form.tagIds = this.form.tagIds.join(",");
          }
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

    /** 提交按钮解读 */
    submitFormInterpretation() {
      this.$refs["formInterpretation"].validate(valid => {
        if (valid) {
          if (this.formInterpretation.id != null) {
            editInterpretationPolicy(this.formInterpretation).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openInterpretation = false;
              this.getList();
            });
          } else {
            addInterpretationPolicy(this.formInterpretation).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openInterpretation = false;
              this.getList();
            });
          }
        } 
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除政策管理编号为"' + ids + '"的数据项？').then(function () {
        return delOriginalpolicy(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('scienceandtechnology/originalpolicy/export', {
        ...this.queryParams
      }, `originalpolicy_${new Date().getTime()}.xlsx`)
    }, seeProvince() {
      debugger
      const ProvinceId = 0;
      getCity(ProvinceId).then(response => {
        this.ProvinceList = response
      });



    },
    seeCity(type) {
      getCity(this.form.province).then(response => { this.CityList = response });
      if (type == 2) {
        this.form.city = null;
        this.CityList = null;
        this.DistinctList = null;
        this.form.region = null;
        this.streetList = null;
        this.form.street = null;
      }

    },
    seeDistinct(type) {

      getCity(this.form.city).then(response => {
        this.DistinctList = response
      });
      if (type == 2) {
        this.DistinctList = null;
        this.form.region = null;
        this.streetList = null;
        this.form.street = null;
      }
    },
    seeStreet(type) {
      getCity(this.form.region).then(response => {
        this.streetList = response
      });
      if (type == 2) {
        this.streetList = null;
        this.form.street = null;
      }
    },
    seeDepartment() {
      getDepartment(100).then(response => {
        this.departmentList = response
      });
    }
  },
};
</script>
