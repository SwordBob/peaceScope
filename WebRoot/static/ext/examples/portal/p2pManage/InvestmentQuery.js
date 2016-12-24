// 投资信息查询
//investmentId=" + investmentId + ", amount=" + amount + ", expect=" + expect + ", investDate=" + investDate + ", borrow="
//				+ borrow + ", borrowId=" + borrowId + ", incomeInfo=" + incomeInfo + ", incomeInfoId=" + incomeInfoId + ", userInfo=" + userInfo
//				+ ", userId=" + userId
//				phone=" + phone + ", email=" + email + ", principal=" + principal + ", exceptInterest=" + exceptInterest
//				+ ", paidIn=" + paidIn + ", incomeInterest=" + incomeInterest + ", phone=" + phone + ", borrowAmount=" + borrowAmount + ", interest="
//				+ interest + ", stages=" + stages + ", email=" + email + ", type=" + type + ", borrowDate=" + borrowDate + ", state=" + state
//				+ ", bidName=" + bidName + ", gender=" + gender + ", marriage=" + marriage + ", address=" + address
Ext.define('App.InvestmentQueryWindow', {
	extend : 'Ext.window.Window',
	constructor : function(config) {
		
		config = config || {};
		Ext.apply(config, {
			constrain:true,
			title : '借款项目信息',
			width : 350,
			height : 560,
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
						name : 'investmentId'
					},{
						xtype : 'hiddenfield',
						name : 'incomeInfoId'
					},{
						xtype : 'hiddenfield',
						name : 'userId'
					},{
						xtype : 'textfield',
						fieldLabel : '借款ID',
						name : 'borrowId'					
					},{
						xtype : 'textfield',
						fieldLabel : 'bidType',
						name : 'bidType'					
					},{
						xtype : 'textfield',
						fieldLabel : '用户手机',
						name : 'phone'					
					},{
						xtype : 'textfield',
						fieldLabel : 'stages',
						name : 'stages'					
					},{
						xtype : 'textfield',
						fieldLabel : 'email',
						name : 'email'					
					},{
						xtype : 'textfield',
						fieldLabel : 'type',
						name : 'type'					
					},{
						xtype : 'textfield',
						fieldLabel : 'investDate',
						name : 'investDate'					
					},{
						xtype : 'textfield',
						fieldLabel : 'state',
						name : 'state'					
					},{
						xtype : 'textfield',
						fieldLabel : 'bidName',
						name : 'bidName'					
					},{
						xtype : 'textfield',
						name : 'amount',
						fieldLabel : '投资金额',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'textfield',
						name : 'expect',
						fieldLabel : '预计收益',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'textfield',
						name : 'principal',
						fieldLabel : '应收本金',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'textfield',
						name : 'exceptInterest',
						fieldLabel : '应收利息',
						maxLength : 200,
						allowBlank : false
						
					},{
						xtype : 'textfield',
						fieldLabel : 'address',
						name : 'address'					
					},{
						xtype : 'textfield',
						fieldLabel : '投资标题',
						name : 'title'					
					}],
					buttons : [ '->', {
						id : 'investmententrywindow-save',
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
								url : appBaseUri + '/sys/investment/saveInvestment',
								params : {
									cmd : vals['cmd'],
									auditState : vals['auditState'],
									title:vals['title'],
									investmentId:vals['investmentId'],
									phone:vals['phone'],
									userId:vals['userId'],
									incomeInfoId:vals['incomeInfoId'],																
									otherInfoUrl:vals['otherInfoUrl'],
									borrowId:vals['borrowId']
								},
								method : "POST",
								success : function(response) {
									if (response.responseText != '') {
										var res = Ext.JSON.decode(response.responseText);
										if (res.success) {
											globalObject.msgTip('操作成功！');
											Ext.getCmp('investmentquerygrid').getStore().reload();
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
						id : 'investmententrywindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'investmententrywindow-accept',
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
			App.InvestmentQueryWindow.superclass.constructor.call(this, config);
		}
	});				
				
				
Ext.define('p2p.app.p2pManage.InvestmentQuery', {
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
			fields : [ {
				name : 'investmentId',
				type : 'int'
			},'userId','title',{
				name : 'borrowId',
				type : 'int'
			},'phone','bidType' ,'incomeInfoId',{
					name : 'amount',
					type : 'float'
				},{
					name : 'expect',
					type : 'float'
				},{
					name : 'principal',
					type : 'float'
				},{
					name : 'exceptInterest',
					type : 'float'
				},{
				name : 'stages',
				type : 'int'
			},'email', 'type', 'investDate', 'state', 
			'bidName','address','auditState']
		});
		var sysUserNameStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/sysuser/getSysUserName',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'UserNameValue'
				}
			},
			fields : [ 'UserNameText', 'UserNameValue' ]
		});
		var repaymentDateStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/p2p/getRepaymentDate',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'ItemValue'
				}
			},
			fields : [ 'ItemText', 'ItemValue' ]
		});
		var investmentQuerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/investment/getInvestment',
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

	var columns = [ {
				text : "用户ID",
				dataIndex : 'userId',
				width : '5%'
			}, {
			text : "借款标ID",
			dataIndex : 'borrowId',
			width : '6%'
			
		},{
			text : "用户手机",
			dataIndex : 'phone',
			width : '10%'
			
			
		},{
			text : "标种",
			dataIndex : 'bidType',
			width : '10%'
			
		},{
			text : "还款方式",
			dataIndex : 'type',
			width : '10%'
			
		},{
			text : "用户邮箱",
			dataIndex : 'email',
			width : '10%'
			
			
		},{
			text : "投资ID",
			dataIndex : 'investmentId',
			width : '10%'
		}, {
			text : "投资标题",
			dataIndex : 'title',
			width : '20%'
			
		},{
				text : "投资金额",
				dataIndex : 'amount',
				width : '8%'
							
			},{
				text : "预计收益",
				dataIndex : 'expect',
				width : '8%'
					
			},{
				text : "收益ID",
				dataIndex : 'incomeInfoId',
				width : '6%'
			},{
				text : "应收本金",
				dataIndex : 'principal',
				width : '8%'
				
			},{
				text : "应收利息",
				dataIndex : 'exceptInterest',
				width : '8%'
						
			},{
			text : "投标状态",
			dataIndex : 'state',
			width : '8%'
			
			
		},{
			xtype : 'actioncolumn',
			width : '18%',			
			items : [{
				iconCls : 'icon-view',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
				tooltip : '查看',
				handler : function(grid, rowIndex, colIndex) {
					var gridRecord = grid.getStore().getAt(rowIndex);
					var win = new App.InvestmentQueryWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('investmentId').setReadOnly(true);
						form.findField('incomeInfoId').setReadOnly(true);
						form.findField('title').setReadOnly(true);
						form.findField('auditState').setReadOnly(true);
						form.findField('phone').setReadOnly(true);
						form.findField('amount').setReadOnly(true);
						form.findField('expect').setReadOnly(true);
						form.findField('investDate').setReadOnly(true);
						form.findField('principal').setReadOnly(true);
						form.findField('exceptInterest').setReadOnly(true);
						
						
						Ext.getCmp('investmententrywindow-cancel').hide();
						Ext.getCmp('investmententrywindow-accept').show();
						win.show();
					}
			} ,{
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.InvestmentQueryWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						form.findField('investmentId').setReadOnly(true);
						form.findField('incomeInfoId').setReadOnly(true);
						form.findField('title').setReadOnly(true);
						form.findField('investDate').setReadOnly(true);
					//	form.findField('auditState').setReadOnly(true);
						form.findField('borrowId').setReadOnly(true);
						form.findField('phone').setReadOnly(true);
						form.findField('principal').setReadOnly(true);
						form.findField('exceptInterest').setReadOnly(true);
						
						
						win.show();
					}
				}]}];
		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'investmentQuerystore-investmentId',
				name : 'investmentId',
				fieldLabel : '投资ID',
				labelWidth : 60,
				width : '10%'
			},{
				xtype : 'textfield',
				id : 'investmentQuerystore-borrowId',
				name : 'borrowId',
				fieldLabel : '借款标ID',
				labelWidth : 60,
				width : '10%'
			},{
				xtype : 'textfield',
				id : 'investmentQuerystore-userId',
				name : 'userId',
				fieldLabel : '用户ID',
				labelWidth : 60,
				width : '10%'
			},{
				xtype : 'textfield',
				fieldLabel : '投资标题',
				id : 'investmentQuerystore-title',
				name : 'title',				
				labelWidth : 60,
				width : '35%',
				maxWidth : 120
			}, {
				xtype : 'textfield',
				id : 'investmentQuerystore-phone',				
				name : 'phone',
				fieldLabel : '用户手机',
				labelWidth : 60,
				width : '13%'
			}, /*{
				xtype : 'combobox',
				id : 'investmentQuerystore-auditState',
				name : 'auditState',
				fieldLabel : '审核状态',
				store: [['复审中','复审中'],['复审通过','复审通过']],
				valueField : 'StateValue',
				displayField : 'StateText',
				typeAhead : true,
				queryMode : 'local',
				emptyText : '请选择...',
				allowBlank : false,
				editable : true,
				labelWidth : 60,
				width : '35%',
				maxWidth : 180
				
			},*//*{
				xtype : 'combobox',
				fieldLabel : '还款日期',
				id : 'investmentQuerystore-repaymentDate',
				name : 'repaymentDate',
				store : repaymentDateStore,
				valueField : 'ItemValue',
				displayField : 'ItemText',
				typeAhead : true,
				queryMode : 'remote',
				emptyText : '请选择...',
				editable : false,
				labelWidth : 90,
				width : '30%',
				maxWidth : 220
			}, */{
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Query'),
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					var searchParams = {
						investmentId : Ext.getCmp('investmentQuerystore-investmentId').getValue(),
						title : Ext.getCmp('investmentQuerystore-title').getValue(),
						phone : Ext.getCmp('investmentQuerystore-phone').getValue(),
						userId : Ext.getCmp('investmentQuerystore-userId').getValue(),
						borrowId : Ext.getCmp('investmentQuerystore-borrowId').getValue()
					};
					Ext.apply(investmentQuerystore.proxy.extraParams, searchParams);
					investmentQuerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('investmentQuerystore-investmentId').setValue(null);
					Ext.getCmp('investmentQuerystore-title').setValue(null);
					Ext.getCmp('investmentQuerystore-phone').setValue(null);
					Ext.getCmp('investmentQuerystore-userId').setValue(null);
					Ext.getCmp('investmentQuerystore-borrowId').setValue(null);
				}
			} ]
		});


		Ext.apply(this, {
			id : 'investmentquerygrid',
			store : investmentQuerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : investmentQuerystore,
				displayInfo : true
			})
		
		});
	
		this.callParent(arguments);
	}
});