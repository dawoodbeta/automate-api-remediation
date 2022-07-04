import os, pprint
from ordered_set import OrderedSet

template_api_path = "../sample_files/base-template/"
source_api_path = "../sample_files/api-current/"
destination_api_path = "../sample_files/api-destination/"

filename = "build.gradle"
template_file = os.path.join(template_api_path, filename)
source_file = os.path.join(source_api_path, filename)
destination_file = os.path.join(destination_api_path, filename)

# with open(template_file) as file:
#     for line in file:
#         print(line.rstrip())

with open(template_file, "r") as f, open(source_file, "r") as out:
    template_line_start = 0
    template_line_end = 0
    for index, line in enumerate(f):
        if line.startswith("dependencies {"):
            template_line_start = index
        if template_line_start !=0 and line.startswith("}"):
            template_line_end = index
            break

    source_line_start = 0
    source_line_end = 0
    for index_out, line_out in enumerate(out):
        if line_out.startswith("dependencies {"):
            source_line_start = index_out
        if source_line_start !=0 and line_out.startswith("}"):
            source_line_end = index_out
            break

with open(template_file, "r") as f:
    i = 0
    template_dict_list = []
    template_list = []
    for index, line in enumerate(f):
        if index > template_line_start and index < template_line_end and line !='\n':
            template_list.append(line)
            data = {}
            template_split_line = line.strip().replace("'", "").replace("ion ", "ion:").replace(" ", "")
            # print("Line {}: {}".format(index, template_split_line))
            i += 1
            if i == 1 or i == 8 or i >= 13:
                line_data = template_split_line.split(":")
                if len(line_data) > 1:
                    ver_list = line_data[len(line_data)-1].split('.')
                    ver_list = [int(x) for x in ver_list]

                    data["prefix"] = line_data[0]
                    data["group"] = line_data[len(line_data)-3]
                    sufix = line_data[len(line_data)-2].split('-')
                    data["artifact_suffix"] = sufix[len(sufix)-1]
                    data["version"] = line_data[len(line_data)-1]
                    data["version_sum"] = sum(ver_list)
                    data['template_line'] = line
                    data['rule'] = 1
            elif i == 10:
                line_data = template_split_line.replace(",", ":").split(":")
                if len(line_data) > 1:
                    ver_list = line_data[len(line_data)-1].split('.')
                    ver_list = [int(x) for x in ver_list]

                    data["prefix"] = line_data[0]
                    data["group"] = line_data[len(line_data)-5]
                    data["artifact_suffix"] = line_data[len(line_data)-3]
                    data["version"] = line_data[len(line_data)-1]
                    data["version_sum"] = sum(ver_list)
                    data['template_line'] = line
                    data['rule'] = 2
            else:
                line_data = template_split_line.split(":")
                if len(line_data) > 1:
                    data["prefix"] = line_data[0]
                    data["group"] = line_data[len(line_data)-2]
                    sufix = line_data[len(line_data)-1].split('-')
                    data["artifact_suffix"] = sufix[len(sufix)-1]
                    data["version"] = None
                    data["version_sum"] = 0
                    data['template_line'] = line
                    data['rule'] = None
            template_dict_list.append(data)

# pprint.pprint(template_dict_list)
# pprint.pprint(template_list)

with open(source_file, "r") as f:
    template_set = OrderedSet()
    unchange_set = OrderedSet()
    change_set = OrderedSet()
    for index_out, line_out in enumerate(f):
        if index_out > source_line_start and index_out < source_line_end:
#             print("Line {}: {}".format(index_out, line_out.strip()))
            for l in template_dict_list:
                if l['group'] in line_out and l['artifact_suffix'] in line_out:
                    template_set.add(l['template_line'])
                    change_set.add(line_out)
                else:
                    unchange_set.add(line_out)

d = {}
for t in template_dict_list:
    for i, u in enumerate(unchange_set):
        if t['group'] in u and t['artifact_suffix'] in u:
            template_split_line = u.strip().replace("'", "").replace("pile ", "pile:").replace(" ", "")
            line_data = template_split_line.split(":")
            if len(line_data) > 1 and t['rule'] == 1:
                ver_list = line_data[len(line_data)-1].split('.')
                ver_list = [int(x) for x in ver_list]

                d["version"] = line_data[len(line_data)-1]
                d["version_sum"] = sum(ver_list)
                d['template_line'] = u

                if d["version_sum"] > t['version_sum']:
                    for tmp in template_set:
                        if t['group'] in tmp and t['artifact_suffix'] in tmp:
                            template_set.remove(tmp)
                            add_line = t['template_line'].replace(str(t['version']), str(d["version"]))
                            template_set.add(add_line)

# pprint.pprint(template_set)
# pprint.pprint(unchange_set)
# pprint.pprint(change_set)
diff = (unchange_set - change_set)
# pprint.pprint(diff)

compose_template = []
compose_template.append(['\n\ndependencies {\n'])
compose_template.append(template_set)
compose_template.append(diff)
compose_template.append(['}\n'])

compose_template = [line.replace('compile', 'implementation').replace('testCompile', 'testImplementation') for level in compose_template for line in level]
# pprint.pprint(compose_template)

with open(source_file, "r") as f:
    final_data_list = []
    for index_, line_ in enumerate(f):
        if index_ > source_line_start-1 and index_ < source_line_end+1:
            continue
        final_data_list.append(line_)
# print(final_data_list)

for line in compose_template:
    final_data_list.append(line)

# pprint.pprint(final_data_list)

with open(destination_file, "w+") as f:
    for line in final_data_list:
        f.writelines(line)
