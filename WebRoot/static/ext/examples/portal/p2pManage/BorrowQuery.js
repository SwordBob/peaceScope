// 借款项目信息

Ext.define('App.BorrowQueryWindow', {
	extend : 'Ext.window.Window',
	constructor : function(config) {
		
		config = config || {};
		Ext.apply(config, {
			constrain:true,
			title : '借款项目信息',
			width : 350,
			height : 700,
			y:2,
			bodyPadding : '10 5',
			layout : 'fit',
			modal : true,
			items : [ {
					xtype : 'form',
					fieldDefaults : {
						labelAlign : 'left',
						labelWidth : 90,
						anchor : '100%'
					},
					items : [ {
						name : "cmd",
						xtype : "hidden",
						value : 'new'
					},{
						xtype : 'hiddenfield',
						name : 'carInfoId'
					},{
						xtype : 'hiddenfield',
						name : 'userId'
					},{
						xtype : 'hiddenfield',
						name : 'borrowId'
					},{
						xtype : 'textfield',
						fieldLabel : '借款人',
						name : 'bidName'					
					},{
						xtype : 'textfield',
						fieldLabel : '性别',
						name : 'gender'					
					},{
						xtype : 'textfield',
						fieldLabel : '婚姻情况',
						name : 'marriage'					
					},{
						xtype : 'textfield',
						fieldLabel : '用户电话',
						name : 'userInfoPhone'					
					},{
						xtype : 'textfield',
						fieldLabel : '用户地址',
						name : 'address'					
					},{
						xtype : 'textfield',
						name : 'title',
						fieldLabel : '借款标题',
						maxLength : 200,
						allowBlank : false
					},{
						xtype : 'textfield',
						name : 'amount',
						fieldLabel : '借款金额',
						maxLength : 200,
						allowBlank : false
					}, {
						xtype : 'textfield',
						name : 'interest',
						fieldLabel : '年利率',
						maxLength : 200,
						allowBlank : false
					}, {
						xtype : 'textfield',
						name : 'stages',
						fieldLabel : '项目期限',
						maxLength : 200,
						allowBlank : false,
						editable:false
					}, {
						xtype : 'textfield',
						name : 'bidType',
						fieldLabel : '标种',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'textfield',
						name : 'type',
						fieldLabel : '还款方式',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'combobox',
						name : 'state',
						fieldLabel : '借款状态',
						store : [['初审中','初审中'],['初审通过','初审通过']],
						valueField : 'StateValue',
						displayField : 'StateText',
						typeAhead : true,
						queryMode : 'local',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false
					}, {
						xtype : 'datefield',
						name : 'borrowDate',
						fieldLabel : '借款日期',
						format : 'Y-m-d H:i:s'
						
					}, {
						xtype : 'textfield',
						name : 'mileage',
						fieldLabel : '公里数',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'textfield',
						name : 'orgPrice',
						fieldLabel : '购买价格',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'textfield',
						name : 'evaluate',
						fieldLabel : '抵押价格',
						maxLength : 200,
						allowBlank : false
						
					},{
						id:'test',
						xtype : 'datefield',
						fieldLabel : '还款日期',
						format : 'Y-m-d H:i:s',
						name : 'repaymentDate'
						
					}, {
						xtype : 'textfield',
						fieldLabel : '还款截止日期',
						name : 'repaymentEnd'
						},{
						xtype : 'hiddenfield',
						//id : 'borrowEntryForm-repaymentStateId',
						name : 'repayId'
					},{
						//id:'testt',
						xtype : 'datefield',
						name : 'auditDate',
						fieldLabel : '审核时间',
						format : 'Y-m-d H:i:s'
					
					},{
						xtype : 'textfield',
						fieldLabel : '审核人',
						name : 'auditor'
						
					}],
					buttons : [ '->', {
						id : 'borrowentrywindow-save',
						text : '保存',
						iconCls : 'icon-save',
						width : 80,
							handler : function(btn, eventObj) {					
						var dt = new Date();
						var window = btn.up('window');
						var form = window.down('form').getForm();
						//alert(Ext.getCmp('test').getValue);
						if (form.isValid()) {
							window.getEl().mask('数据保存中，请稍候...');
							var vals = form.getValues();
								//alert(appBaseUri + '/sys/borrow/saveBorrow');
								Ext.Ajax.request({
								url : appBaseUri + '/sys/borrow/saveBorrow',
								params : {
									cmd : vals['cmd'],
									title : vals['title'],
									state : vals['state'],
									repayId:vals['repayId'],
									//borrowDate:dt,
									interest:vals['interest'],
									stages:vals['stages'],
									amount:vals['amount'],
									gender:vals['gender'],
									marriage:vals['marriage'],
									address:vals['address'],
									userId:vals['userId'],
									carInfoId:vals['carInfoId'],
									borrowId:vals['borrowId'],
									bidType:vals['bidType'],
									type:vals['type'],
									carId:vals['carId'],
									//carBrand:vals['carInfoId'],
									//mileage:vals['mileage'],
									bidName:vals['bidName'],
									//t:vals['t'],
									//auditDate:dt,
									testDate:vals['borrowDate'],
									auditor:vals['auditor']
								},
								method : "POST",
								success : function(response) {
									if (response.responseText != '') {
										var res = Ext.JSON.decode(response.responseText);
										if (res.success) {
											globalObject.msgTip('操作成功！');
											Ext.getCmp('borrowquerygrid').getStore().reload();
										} else {
											globalObject.errTip(res.message);
										}
									}
								},
								failure : function(response) {
									globalObject.errTip('操作失败！');
								}
							});
								window.getEl().unmask();
								window.close();
							}
						}
					}, {
						id : 'borrowentrywindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'borrowentrywindow-accept',
						text : '确定',
						hidden : true,
						iconCls : 'icon-accept',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, '->' ]
				} ]
			});
			App.BorrowQueryWindow.superclass.constructor.call(this, config);
		}
	});
	
Ext.define('p2p.app.p2pManage.BorrowQuery', {
	extend : 'Ext.grid.Panel',
	region : 'center',
	initComponent : function() {
		var me = this;
		/*var stateStore = new Ext.data.SimpleStore({
			fields : [ 'StateValue', 'StateText' ],
			data : [ [ false, '否' ], [ true, '是' ] ]
		});*/
		
	

		Ext.define('ModelList', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [{
					name : 'id',
					type : 'long'
				},  'userId', 'carInfoId','bidName', 'userInfoPhone', 'userInfoId', 'borrowId', 'title','amount','interest','stages','bidType','type','gender','marriage','address', {
					name : 'state',
					type : 'string'
				}, {
					name : 'borrowDate',
					type : 'datetime',
					dateFormat : 'Y-m-d H:i:s'					
				}, {
					//id:'test',
				name : 'repaymentDate',
				type : 'string'	
				
			},{
				name : 'repaymentEnd',
				type : 'datetime'		
				
			},{
				//id:'testt',
				name : 'auditDate',
				type : 'string',		
				dateFormat : 'Y-m-d H:i:s'  
				
			},'carBrand','carId',{
					name : 'mileage',
					type : 'int'
				},{
					name : 'orgPrice',
					type : 'float'
				},{
					name : 'evaluate',
					type : 'float'
				},'auditor','repayId' ]
			});
		/*var accountStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/userInfo/getAccountId',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'AccountValue'
				}
			},
			fields : [ 'AccountText', 'AccountValue' ]
		});*/
		var repaymentDateStore = Ext.create('Ext.data.JsonStore', {
		proxy : {			
			type : 'ajax',
			url : appBaseUri + '/sys/borrow/getRepaymentDate',
			reader : {
				type : 'json',
				root : 'list',
				idProperty : 'ItemValue'
			}
		},
		fields : [ 'ItemText', 'ItemValue' ]
	});
	var userInfoNameStore = Ext.create('Ext.data.JsonStore', {
		proxy : {
			type : 'ajax',
			url : appBaseUri + '/sys/userInfo/getUserInfoName',
			reader : {
				type : 'json',
				root : 'list',
				idProperty : 'UserNameValue'
			}
		},
		fields : [ 'UserNameText', 'UserNameValue' ]
	});

		//重要---------------------
		var borrowQuerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/borrow/getBorrow',
				extraParams : me.extraParams || null,
				reader : {
					type : 'json',
					root : 'data',
					totalProperty : 'totalRecord',
					successProperty : "success"
				}
			},
			sorters : [ {
				property : 'id',
				direction : 'DESC'
			} ]
		});

		var columns = [  {
				text : "用户ID",
				dataIndex : 'userId',
				width : '5%'
			}, {
				text : "借款人",
				dataIndex : 'bidName',
				width : '9%'
			}, /*{
				text : "性别",
				dataIndex : 'gender',
				width : '5%'
			},*//* {
				text : "婚姻情况",
				dataIndex : 'marriage',
				width : '6%'
			}, */{
				text : "用户电话",
				dataIndex : 'userInfoPhone',
				width : '13%'				
			},/*{
				text : "用户地址",
				dataIndex : 'address',
				width : '8%'
			},*/{
				text : "借款ID",
				dataIndex : 'borrowId',
				width : '6%'
			}, {
				text : "借款标题",
				dataIndex : 'title',
				width : '14%'
			}, {
				text : "借款金额",
				dataIndex : 'amount',
				width : '8%'
			},{
				text : "年利率",
				dataIndex : 'interest',
				width : '5%'
			},{
				text : "期限",
				dataIndex : 'stages',
				width : '6%'
			},{
				text : "标种",
				dataIndex : 'bidType',
				width : '7%'
			},{
				text : "还款方式",
				dataIndex : 'type',
				width : '7%'
			},{
				text : "借款日期",
				dataIndex : 'borrowDate',
				width : '14%'
			},{
				text : "车辆编号",
				dataIndex : 'carInfoId',
				width : '6%'
			},/*{
				text : "车辆品牌",
				dataIndex : 'carBrand',
				width : '6%'
			},*/{
				text : "车牌号",
				dataIndex : 'carId',
				width : '6%'
			},{
				text : "购买价格",
				dataIndex : 'orgPrice',
				width : '6%',
				hidden:true					
			},{
				text : "抵押价格",
				dataIndex : 'evaluate',
				width : '6%',
				hidden:true					
			},{
				text : "审核状况",
				dataIndex : 'state',
				width : '8%'
						
			}, {
				text : "审核时间",
				dataIndex : 'auditDate',
				width : '14%'
				
			},{
				text : "还款ID",
				dataIndex : 'repayId',
				//sortable : false,
				width : '5%'
			}, {
				text : "还款日期",
				dataIndex : 'repaymentDate',
				width : '14%'							
			}, {
				text : "还款截止日期",
				dataIndex : 'repaymentEnd',
				width : '14%'				
			},{
				text : "审核人",
				dataIndex : 'auditor',
				width : '6%'
			}, {
			xtype : 'actioncolumn',
			width : '8%',			
			items : [ {
				iconCls : 'icon-view',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
				tooltip : '查看',
				handler : function(grid, rowIndex, colIndex) {
					var gridRecord = grid.getStore().getAt(rowIndex);
					var win = new App.BorrowQueryWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('carInfoId').setReadOnly(true);
						form.findField('bidName').setReadOnly(true);
						form.findField('gender').setReadOnly(true);
						form.findField('marriage').setReadOnly(true);
						form.findField('address').setReadOnly(true);
						form.findField('userInfoPhone').setReadOnly(true);
						form.findField('title').setReadOnly(true);
						form.findField('amount').setReadOnly(true);
						form.findField('interest').setReadOnly(true);
						form.findField('stages').setReadOnly(true);
						form.findField('bidType').setReadOnly(true);
						form.findField('type').setReadOnly(true);
						form.findField('state').setReadOnly(true);
						form.findField('mileage').setReadOnly(true);
						form.findField('orgPrice').setReadOnly(true);
						form.findField('evaluate').setReadOnly(true);
						form.findField('borrowDate').setReadOnly(true);
						form.findField('borrowId').setReadOnly(true);
						form.findField('repaymentDate').setReadOnly(true);
						form.findField('repaymentEnd').setReadOnly(true);
						form.findField('auditDate').setReadOnly(true);
						form.findField('auditor').setReadOnly(true);
						Ext.getCmp('borrowentrywindow-save').hide();
						Ext.getCmp('borrowentrywindow-cancel').hide();
						Ext.getCmp('borrowentrywindow-accept').show();
						win.show();
					}
			} ,{
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.BorrowQueryWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						//form.findField("userId").setReadOnly(true);
						form.findField('gender').setReadOnly(true);
						form.findField('marriage').setReadOnly(true);
						form.findField('address').setReadOnly(true);
						form.findField("userInfoPhone").setReadOnly(true);
						form.findField('bidName').setReadOnly(true);
						form.findField("title").setReadOnly(true);
						form.findField('amount').setReadOnly(true);
						form.findField('interest').setReadOnly(true);
						form.findField('stages').setReadOnly(true);
						form.findField('bidType').setReadOnly(true);
						form.findField('type').setReadOnly(true);						
						form.findField("borrowDate").setReadOnly(true);
						form.findField('auditDate').setReadOnly(true);
						form.findField('auditor').setReadOnly(true);						
						win.show();
					}
				}]}];

		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'borrowQuery-userId',
				name : 'userId',
				fieldLabel : '用户ID',
				labelWidth : 50,
				width : '10%'
			},{
				xtype : 'textfield',
				fieldLabel : '借款标题',
				id : 'borrowQuery-title',
				name : 'title',
				/*store : accountStore,
				valueField : 'AccountValue',
				displayField : 'AccountText',
				typeAhead : true,
				queryMode : 'remote',
				editable : true,*/
				labelWidth : 60,
				width : '35%',
				maxWidth : 180
			},{
				xtype : 'combobox',
				id : 'borrowQuery-state',
				name : 'state',
				fieldLabel : '借款状态',
				//[[ '未审核', '未审核' ],[ '初审中', '初审中' ], [ '初审通过', '初审通过' ]，[ '已满标', '已满标' ]，[ '流标', '流标' ] ],
				store: [['已满标','已满标'],[ '未审核','未审核'],['初审中','初审中'],['初审通过','初审通过'],['流标', '流标' ]],
				typeAhead : true,
				queryMode : 'local',
				emptyText : '请选择...',				
				editable : true,
				labelWidth : 60,
				width : '25%',
				maxWidth : 150				
			},{
				xtype : 'textfield',
				fieldLabel : '用户电话',
				id : 'borrowQuery-userInfoPhone',
				name : 'userInfoPhone',			
				editable : true,
				labelWidth : 70,
				width : '35%',
				maxWidth : 180
			}, {
				xtype : 'datefield',
				id : 'borrowQuery-repaymentEnd',				
				name : 'repaymentEnd',
				fieldLabel : '还款截止日期',
				labelWidth : 55,
				width : '19%',
				format : 'Y-m-d'
			}, {
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Query'),
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					
					var searchParams = {
							userId : Ext.getCmp('borrowQuery-userId').getValue(),
							userInfoPhone : Ext.getCmp('borrowQuery-userInfoPhone').getValue(),
							repaymentEnd : Ext.getCmp('borrowQuery-repaymentEnd').getValue(),
							title : Ext.getCmp('borrowQuery-title').getValue(),
							state: Ext.getCmp('borrowQuery-state').getValue()
//						repaymentStateId : Ext.getCmp('borrowQuery-repaymentDate').getValue()
					};
					Ext.apply(borrowQuerystore.proxy.extraParams, searchParams);
					borrowQuerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('borrowQuery-userId').setValue(null);
					Ext.getCmp('borrowQuery-userInfoPhone').setValue(null);
					Ext.getCmp('borrowQuery-repaymentEnd').setValue(null);
					Ext.getCmp('borrowQuery-title').setValue(null);
					Ext.getCmp('borrowQuery-state').setValue(null);
//					Ext.getCmp('borrowQuery-repaymentDate').setValue(null);
				}
			} ]
		});

		Ext.apply(this, {
			id : 'borrowquerygrid',
			store : borrowQuerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : borrowQuerystore,
				displayInfo : true
			})
		/*	listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					var grid = this;
					var id = grid.getSelectionModel().getSelection()[0].get('id');
					var phone = grid.getSelectionModel().getSelection()[0].get('phone');
					var phone = grid.getSelectionModel().getSelection()[0].get('phone');
					alert(phone);
					var gridRecord = grid.getStore().findRecord('id', id);
					var win = new App.BorrowQuery({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('userId').setReadOnly(true);
					//form.findField('account').setReadOnly(true);
					form.findField('phone').setReadOnly(true);
					form.findField('plantTime').setReadOnly(true);
					form.findField('entryTime').setReadOnly(true);
					//form.findField('repaymentDate').setReadOnly(true);
					//win.show();
					 w = new Ext.window.Window({					      
					       width:500,				      
					       height:400,closeAction:'hide',
					       constrain:true,
					       layout:'fit',
					       modal:true,
					       html:'<iframe style=width:490px;height:400px src="../p2p/getRepaymentDate"></iframe>',
					       title:"标题" 
					      // autoScroll:true
					    });						
					w.show();	
				}
			}*/
		});
	
		this.callParent(arguments);
	}
});