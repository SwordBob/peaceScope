// 账户流水信息查询
Ext.define('p2p.app.p2pManage.AccountRecordQuery', {
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
				name : 'accountRecordId',
				type : 'int'
			},{
				name : 'accountId',
				type : 'int'
			},{
				name : 'amount',
				type : 'float'
			},'tradeProcesses','accountType',{
				name : 'tradeTime',
				type : 'datetime',
				dateFormat : 'Y-m-d H:i:s'
			}, 'tradeTraget']
		});
		
		/*var repaymentDateStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/accountRecord/getRepaymentDate',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'ItemValue'
				}
			},
			fields : [ 'ItemText', 'ItemValue' ]
		});*/
		var accountRecordQuerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/accountRecord/getAccountRecord',
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
			text : "账户流水ID",
			dataIndex : 'accountRecordId',
			width : '7%'
		}, {
			text : "交易账户类型",
			dataIndex : 'accountType',
			width : '12%'		
		}, {
			text : "交易金额",
			dataIndex : 'amount',
			width : '11%'
		}, {
			text : "交易目标",
			dataIndex : 'tradeTraget',
			width : '15%'						
		},{
			text : "交易时间",
			dataIndex : 'tradeTime',
			width : '17%'
		},{
			text : "交易类型",
			dataIndex : 'tradeProcesses',
			width : '13%'
		},{
			text : "账户ID",
			dataIndex : 'accountId',
			width : '15%'
		}];

		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'accountRecordQuery-accountRecordId',
				name : 'accountRecordId',
				fieldLabel : '账户流水ID',
				labelWidth : 60,
				width : '15%'
			},{
				xtype : 'textfield',
				id : 'accountRecordQuery-accountId',
				name : 'accountId',
				fieldLabel : '账户ID',
				labelWidth : 55,
				width : '15%'
			},{
				xtype : 'combobox',
				id : 'accountRecordQuery-tradeProcesses',
				name : 'state',
				fieldLabel : '交易类型',
				store: [['投资','投资'],['充值','充值'],['还款','还款']],
				valueField : 'StateValue',
				displayField : 'StateText',
				typeAhead : true,
				queryMode : 'local',
				emptyText : '请选择...',				
				editable : true,
				width : '35%',
				labelWidth : 60,
				maxWidth : 180
				
			},{
				xtype : 'datefield',
				id : 'accountRecordQuery-tradeTime',				
				name : 'repaymentEnd',
				fieldLabel : '交易时间',
				labelWidth : 60,
				width : '19%',
				format : 'Y-m-d'
			},{
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Query'),
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					var searchParams = {
						accountRecordId : Ext.getCmp('accountRecordQuery-accountRecordId').getValue(),
						
						tradeTime : Ext.getCmp('accountRecordQuery-tradeTime').getValue(),
						tradeProcesses : Ext.getCmp('accountRecordQuery-tradeProcesses').getValue(),
						accountId : Ext.getCmp('accountRecordQuery-accountId').getValue()
					};
					Ext.apply(accountRecordQuerystore.proxy.extraParams, searchParams);
					accountRecordQuerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('accountRecordQuery-accountRecordId').setValue(null);					
					Ext.getCmp('accountRecordQuery-tradeTime').setValue(null);
					Ext.getCmp('accountRecordQuery-tradeProcesses').setValue(null);
					Ext.getCmp('accountRecordQuery-accountId').setValue(null);
		
				}
			} ]
		});

		Ext.apply(this, {
			store : accountRecordQuerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : accountRecordQuerystore,
				displayInfo : true
			})
			
		});
	
		this.callParent(arguments);
	}
});