// 登陆人数变化表
Ext.require([ 'Ext.chart.*', 'Ext.layout.container.Fit', 'Ext.window.MessageBox', 'Ext.grid.Panel' ]);

Ext.define('p2p.app.report.LoginInfoReport', {
	extend : 'Ext.panel.Panel',
	initComponent : function() {

		var store1 = Ext.create('Ext.data.JsonStore', {
			fields : [ 'ltime', 'gc', 'mount1' ],
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/loginInfo/getLoginInfoStatistics',
				reader : {
					type : 'array',
					root : ''
				}
			}
		})

		var chart = Ext.create('Ext.chart.Chart', {
			animate : true,
			shadow : true,
			store : store1,
			//autoScroll:true,
			//overflowX: 'auto',
			autoSize:true,
			axes : [ {
				type : 'Numeric',
				position : 'left',
				fields : [ 'mount1' ],
				//数量缩写Qty
				title : 'Qty',
				grid : true
			}, {
				type : 'Category',
				position : 'bottom',
				fields : [ 'ltime' ]
			} ],
			series : [ {
				type : 'line',
				axis : 'left',
				gutter : 80,
				xField : 'ltime',
				yField : [ 'mount1' ],
				tips : {
					trackMouse : true,
					width : 270,
					height : 70,
					layout : 'fit',
					renderer : function(klass, item) {
						var storeItem = item.storeItem, data = [ {
							name : 'mount1',
							data : storeItem.get('mount1')
						} ], i, l, html;
						this.setTitle("时刻：" + storeItem.get('ltime') + "<br/>用户ID：" + storeItem.get('gc') + "<br/>人数：" + storeItem.get('mount1') + "个");
					}
				}
			} ]
		});

		var panel1 = Ext.create('widget.panel', {
			layout : 'fit',
			/**
			 * <code>
			tbar : [ {
				text : '保存报表',
				handler : function() {
					Ext.MessageBox.confirm('系统信息', '确认将报表保存成图片？', function(choice) {
						if (choice == 'yes') {
							chart.save({
								type : 'image/png'
							});
						}
					});
				}
			} ],
			 *</code>
			 */
			items : chart
		});

		Ext.apply(this, {
			layout : 'fit',
			region : "center",
			items : [ panel1 ]
		});

		this.callParent(arguments);
	}
});
