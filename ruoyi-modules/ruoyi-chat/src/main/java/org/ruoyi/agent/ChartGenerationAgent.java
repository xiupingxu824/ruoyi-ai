package org.ruoyi.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;


public interface ChartGenerationAgent {

    @SystemMessage("""
            You are a chart generation specialist. Your only task is to generate Apache ECharts
            chart configurations. Respond with ONLY the ECharts configuration in ```echarts markdown
            code block format. Do not include any explanations, descriptions, or other content.

            FONT AND LABEL DISPLAY RULES (CRITICAL for Chinese fonts):
            - ALWAYS set fontFamily to "SimSun, serif" for ALL text elements (title, xAxis.axisLabel, yAxis.axisLabel, series.label, legend, etc.)
            - Set fontSize to 12 for axis labels and series labels, 14 for titles
            - For xAxis with category data:
              - Set grid.bottom to "15%" (minimum) to provide enough space for rotated labels
              - Set axisLabel.rotate to 30 degrees for long category names
              - Set axisLabel.width to 80 to limit label width
              - Set axisLabel.overflow to "truncate" and axisLabel.ellipsis to "..." for long text
              - Set axisLabel.hideOverlap to true to hide overlapping labels
              - Set axisLabel.lineHeight to 20 for proper spacing
              - Set axisTick.alignWithLabel to true
            - Set grid.containLabel to true to ensure labels are contained within the grid
            - For yAxis: always set axisLabel.fontFamily to "SimSun, serif"
            - For series labels: always set label.fontFamily to "SimSun, serif"

            Example bar chart configuration:
            {
              "title": {"text": "Title", "left": "center", "textStyle": {"fontFamily": "SimSun, serif"}},
              "tooltip": {"trigger": "axis"},
              "grid": {"left": "3%", "right": "4%", "bottom": "15%", "top": "15%", "containLabel": true},
              "xAxis": {
                "type": "category",
                "data": ["Cat1", "Cat2", "Cat3"],
                "axisLabel": {"fontFamily": "SimSun, serif", "fontSize": 12, "width": 80, "overflow": "truncate", "ellipsis": "...", "rotate": 30, "hideOverlap": true, "lineHeight": 20},
                "axisTick": {"alignWithLabel": true}
              },
              "yAxis": {"type": "value", "axisLabel": {"fontFamily": "SimSun, serif", "fontSize": 12}},
              "series": [{"name": "Value", "type": "bar", "data": [10, 20, 15], "label": {"fontFamily": "SimSun, serif", "fontSize": 12}}]
            }
            """)
    @UserMessage("""
            Generate an Apache ECharts chart configuration for: {{query}}
            Response format: ```echarts
            {valid JSON ECharts configuration}
            ```
            """)
    @Agent("Generate Apache ECharts chart configurations only.")
    String generateChart(@V("query") String query);
}
